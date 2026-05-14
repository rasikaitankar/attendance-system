pipeline {
    agent any

    tools {
        jdk 'JDK21'
        maven 'Maven3'
    }

    environment {
        AWS_ACCOUNT_ID = '952969969075'
        AWS_REGION = 'us-east-1'
        IMAGE_NAME = 'attendance-system'
        REPOSITORY_URI = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${IMAGE_NAME}"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                url: 'https://github.com/rasikaitankar/attendance-system.git'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Build Jar') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t ${IMAGE_NAME}:${BUILD_NUMBER} .'
            }
        }

        stage('Tag Docker Image') {
            steps {
                sh 'docker tag ${IMAGE_NAME}:${BUILD_NUMBER} ${REPOSITORY_URI}:${BUILD_NUMBER}'
            }
        }

        stage('Push To ECR') {
            steps {
                sh '''
                aws ecr get-login-password --region ${AWS_REGION} | \
                docker login --username AWS --password-stdin ${REPOSITORY_URI}

                docker push ${REPOSITORY_URI}:${BUILD_NUMBER}
                '''
            }
        }
    }

    post {
        success {
            echo 'Docker image pushed to ECR successfully'
        }

        failure {
            echo 'Pipeline failed'
        }
    }
}