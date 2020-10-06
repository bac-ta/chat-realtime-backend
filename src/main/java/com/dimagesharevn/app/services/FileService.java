package com.dimagesharevn.app.services;


import com.dimagesharevn.app.components.AppComponentFactory;
import com.dimagesharevn.app.constants.APIMessage;
import com.dimagesharevn.app.enumerations.FileType;
import com.dimagesharevn.app.utils.ResourceNotFoundExceptionHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
public class FileService {
    private final Path fileStorageLocation;
    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    @Autowired
    public FileService(@Qualifier("appComponentFactoryImpl") AppComponentFactory appComponentFactory) {
        this.fileStorageLocation = Paths.get(appComponentFactory.getFileStoreAvatar())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw new ResourceNotFoundExceptionHandler(APIMessage.CREATE_FILEDIR_ERROR, e);
        }
    }

    public String storeFile(MultipartFile file) {
        logger.info("Store file");
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains(".."))
                throw new ResourceNotFoundExceptionHandler(APIMessage.FILE_INVALID_PATH_SEQUENCE + fileName);
            String fileTypeStr = FilenameUtils.getExtension(file.getOriginalFilename());

            //Check file type
            FileType.findByName(fileTypeStr);
            UUID uuid = UUID.randomUUID();
            logger.info("uuid:" + uuid.toString());
            String fileNameGen = uuid.toString().concat(".").concat(fileTypeStr);

            // Copy file to the target location (Replacing existing file with the same name)
            //sau xu ly lai cho nay
            File dir = new File(String.valueOf(fileStorageLocation));
            File[] Filess = dir.listFiles();
            if(Filess != null){
                IntStream.range(0, Filess.length).forEach(j -> {
                    Filess[j].getAbsolutePath();
                    Filess[j].delete();
                });
            }
            //xu lys lai sau
            Path targetLocation = fileStorageLocation.resolve(fileNameGen);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileNameGen;
        } catch (IOException ex) {
            logger.debug(ex.getMessage());
            throw new ResourceNotFoundExceptionHandler(String.format(APIMessage.FILE_NOT_STORE, fileName), ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        logger.info("Load file as resource");
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            System.out.println(filePath);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new ResourceNotFoundExceptionHandler(String.format(APIMessage.FILE_NOT_FOUND, fileName));
            }
        } catch (MalformedURLException e) {
            logger.debug(e.getMessage());
            throw new ResourceNotFoundExceptionHandler(String.format(APIMessage.FILE_NOT_FOUND, fileName), e);
        }
    }
}
