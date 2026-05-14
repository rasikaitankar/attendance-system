pipeline {
    agent any

    tools {
        jdk 'JDK21'
        maven 'Maven3'
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/rasikaitankar/attendance-system.git'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn clean test'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t attendance-system:${BUILD_NUMBER} .'
            }
        }

    }

    post {
        success {
            echo 'CI Pipeline Success'
        }
        failure {
            echo 'CI Pipeline Failed'
        }
    }
}