```groovy
pipeline {

    agent any

    tools {
        jdk 'JDK21'
        maven 'MAVEN'
    }

    stages {

        stage('Clone') {
            steps {
                git 'https://github.com/rutuja8308/user-service.git'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Package') {
            steps {
                bat 'mvn clean package'
            }
        }

        stage('Run Application') {
            steps {
                bat 'java -jar target\\user-service-0.0.1-SNAPSHOT.jar'
            }
        }
    }
}
```
