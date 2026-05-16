pipeline {

    agent any

    environment {

        AWS_REGION = 'us-east-1'
        ECR_REPO = 'attendance-system'
        IMAGE_TAG = "${BUILD_NUMBER}"

        AWS_ACCOUNT_ID = 'AKIA53YLPXWZVXOTIEVW'

        IMAGE_URI =
        "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${ECR_REPO}:${IMAGE_TAG}"
    }

    tools {
        maven 'Maven'
    }

    stages {

        stage('Clone Source') {

            steps {
                git branch: 'main',
                url: 'https://github.com/rasikaitankar/attendance-system.git'
            }
        }

        stage('Run Tests') {

            steps {
                sh 'mvn test'
            }
        }

        stage('Build JAR') {

            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {

            steps {
                sh 'docker build -t attendance-system:${BUILD_NUMBER} .'
            }
        }

        stage('Login to AWS ECR') {

            steps {

                withCredentials([
                    [$class: 'AmazonWebServicesCredentialsBinding',
                    credentialsId: 'aws-creds']
                ]) {

                    sh """
                    aws ecr get-login-password \
                    --region $AWS_REGION | docker login \
                    --username AWS \
                    --password-stdin \
                    ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com
                    """
                }
            }
        }

        stage('Tag Docker Image') {

            steps {

                sh """
                docker tag attendance-system:${BUILD_NUMBER} \
                ${IMAGE_URI}
                """
            }
        }

        stage('Push Docker Image') {

            steps {

                sh """
                docker push ${IMAGE_URI}
                """
            }
        }
    }
}