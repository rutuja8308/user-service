pipeline {
    agent any

    tools {
        jdk 'JDK21'
        maven 'MAVEN'
    }

    environment {
        TOMCAT_HOME = 'C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1'
        WAR_FILE = 'user-service.war'
    }

    options {
        timestamps()
    }

    stages {

        stage('Build WAR') {
            steps {
                bat 'mvn clean clean package'
            }
        }

        stage('Deploy WAR') {
            steps {
                bat '''
                echo Deploying WAR...

                copy /Y "target\\%WAR_FILE%" "%TOMCAT_HOME%\\webapps\\%WAR_FILE%"
                '''
            }
        }

        stage('Verify Deployment') {
            steps {
                bat '''
                timeout /t 10 > nul
                netstat -ano | findstr ":8080"
                '''
            }
        }
    }

    post {
        success {
            echo '🚀 Deployment Successful (Tomcat managed manually/service)'
        }

        failure {
            echo '❌ Deployment Failed'
        }
    }
}