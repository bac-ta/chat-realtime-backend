pipeline {
    agent any
    environment {
        COMPOSE_FILE = "docker-compose.yml"
        PATH = "$PATH:/usr/local/bin"
    }
    tools {
        maven "MAVEN_HOME"
    }
    stages {
        stage("Maven Build Jar File") {
            steps {
                git "https://github.com/bac-ta/chat-realtime-backend.git"
                sh "mvn clean install -DskipTests"
            }
        }
        stage("Docker Compose Build Enviroment") {
            steps {
                sh "docker-compose up -d --build"
            }
        }
    }
}