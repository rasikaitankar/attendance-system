pipeline {

    agent any

    environment {

        AWS_REGION = 'ap-south-1'
        ECR_REPO = 'attendance-system'
        IMAGE_TAG = "${BUILD_NUMBER}"

        AWS_ACCOUNT_ID = '952969969075'

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
                sh 'mvn testttt'
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
                    credentialsId: 'aws-creds-new']
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


//second
// pipeline {
//     agent any

//     parameters {
//         choice(
//             name: 'ENVIRONMENT',
//             choices: ['Staging', 'Production'],
//             description: 'Select deployment environment'
//         )

//         string(
//             name: 'BUILD_NUMBER',
//             defaultValue: '13',
//             description: 'Docker image tag from ECR'
//         )
//     }

//     environment {
//         AWS_ACCOUNT_ID = '952969969075'
//         AWS_REGION = 'us-east-1'
//         IMAGE_NAME = 'attendance-system'
//         CONTAINER_NAME = 'attendance-app'
//     }

//     stages {

//         stage('Login to AWS ECR') {
//             steps {
//                 withCredentials([[
//                     $class: 'AmazonWebServicesCredentialsBinding',
//                     credentialsId: 'aws-creds'
//                 ]]) {

//                     sh '''
//                     aws ecr get-login-password --region $AWS_REGION | \
//                     docker login --username AWS \
//                     --password-stdin $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com
//                     '''
//                 }
//             }
//         }

//         stage('Pull Docker Image') {
//             steps {
//                 sh '''
//                 docker pull $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com/$IMAGE_NAME:$BUILD_NUMBER
//                 '''
//             }
//         }

//         stage('Stop Old Container') {
//             steps {
//                 sh '''
//                 docker stop $CONTAINER_NAME || true
//                 docker rm $CONTAINER_NAME || true
//                 '''
//             }
//         }

//         stage('Run New Container') {
//             steps {
//                 sh '''
//                 docker run -d -p 8081:8080 \
//                 --name $CONTAINER_NAME \
//                 $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com/$IMAGE_NAME:$BUILD_NUMBER
//                 '''
//             }
//         }

//         stage('Verify Deployment') {
//             steps {
//                 sh 'docker ps'
//             }
//         }
//     }

//     post {
//     success {
//         emailext(
//             subject: "SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
//             body: """
// CI Pipeline Successful

// Job Name: ${env.JOB_NAME}
// Build Number: ${env.BUILD_NUMBER}

// Docker Image pushed successfully to AWS ECR.
//             """,
//             to: "itankarrn@rknec.edu"
//         )
//     }

//     failure {
//         emailext(
//             subject: "FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
//             body: """
// CI Pipeline Failed

// Job Name: ${env.JOB_NAME}
// Build Number: ${env.BUILD_NUMBER}

// Please check Jenkins console logs.
//             """,
//             to: "itankarrn@rknec.edu"
//         )
//     }
// }
// }