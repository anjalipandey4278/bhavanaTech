pipeline {
    agent any

    environment {
        SPRING_BOOT_PROJECT_DIR = "C:\\Users\\user\\Desktop\\Interlace_Template"
        SPRING_BOOT_PROJECT_NAME = "InterlaceApplication"
        IIS_WEBAPPS_DIR = "C:\\inetpub\\wwwroot"
        WAR_FILE = "${SPRING_BOOT_PROJECT_DIR}\\target\\${SPRING_BOOT_PROJECT_NAME}.war"
    }

    stages {
        stage('Build') {
            steps {
                script {
                    // Checkout the source code from Git
                    git url: 'git@github.com:anjalipandey4278/bhavanaTech.git'

                    // Build the Spring Boot application
                    bat "cd ${SPRING_BOOT_PROJECT_DIR} && mvnw clean package"
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
    new File(filePath).exists()
}
