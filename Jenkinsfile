pipeline {

    agent any

    tools {
        jdk 'JDK17'
        maven 'Maven3'
    }

    stages {

        stage('Clone') {
            steps {
                git 'https://github.com/rutuja8308/user-service.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Run Application') {
            steps {
                sh 'nohup java -jar target/user-service-0.0.1-SNAPSHOT.jar &'
            }
        }
    }
}