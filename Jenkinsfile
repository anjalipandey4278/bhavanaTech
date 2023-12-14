pipeline {
    agent any

    environment {
        SPRING_BOOT_PROJECT_DIR = "C:\\Users\\user\\Desktop\\Interlace_Template"
        SPRING_BOOT_PROJECT_NAME = "InterlaceApplication"
        IIS_WEBAPPS_DIR = "C:\\inetpub\\wwwroot"
        WAR_FILE = "${SPRING_BOOT_PROJECT_DIR}\\target\\${SPRING_BOOT_PROJECT_NAME}.war"
        MAVEN_HOME = "C:/Program Files/apache-maven-3.9.4"
    }

    tools {
        maven 'MAVEN_HOME'
        git 'Default'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/main']], userRemoteConfigs: [[url: 'git@github.com:anjalipandey4278/bhavanaTech.git']]])
            }
        }

        stage('Build') {
            steps {
                script {
                    // Build the Spring Boot application using Maven
                    bat "\"%MAVEN_HOME%\\bin\\mvn\" -f ${SPRING_BOOT_PROJECT_DIR} clean package"
                }
            }
        }

        stage('Deploy to IIS') {
            steps {
                script {
                    // Check if the WAR file exists
                    if (!fileExists(WAR_FILE)) {
                        error "Error: The WAR file '${WAR_FILE}' does not exist. Build may have failed."
                    }

                    // Copy the WAR file to the IIS webapps directory
                    bat "copy ${WAR_FILE} ${IIS_WEBAPPS_DIR}"

                    // Restart IIS to apply changes
                    bat "iisreset"
                }
            }
        }
    }

    post {
        success {
            echo "Deployment successful. Access your application at http://localhost/${SPRING_BOOT_PROJECT_NAME}"
        }

        failure {
            echo "Deployment failed. Please check the Jenkins logs for details."
        }
    }
}

def fileExists(filePath) {
    return fileExistsInWorkspace(filePath)
}

def fileExistsInWorkspace(filePath) {
    return fileExistsFromWorkspace(filePath)
}

def fileExistsFromWorkspace(String path) {
    return fileExistsFromWorkspace(new File(path))
}

def fileExistsFromWorkspace(File file) {
    return file.exists()
}


