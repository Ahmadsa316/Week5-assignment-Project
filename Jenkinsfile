pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS_ID = 'dockerhub-creds'
        DOCKERHUB_REPO = 'ahmadsa316/week5-assignment-project'
        DOCKER_IMAGE_TAG = 'latest'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'docker run --rm -v "$PWD":/app -w /app maven:3.9.6-eclipse-temurin-17 mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                sh 'docker run --rm -v "$PWD":/app -w /app maven:3.9.6-eclipse-temurin-17 mvn test'
            }
        }

        stage('Coverage Report') {
            steps {
                sh 'docker run --rm -v "$PWD":/app -w /app maven:3.9.6-eclipse-temurin-17 mvn jacoco:report'
            }
        }

        stage('Package') {
            steps {
                sh 'docker run --rm -v "$PWD":/app -w /app maven:3.9.6-eclipse-temurin-17 mvn package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}")
                }
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', DOCKERHUB_CREDENTIALS_ID) {
                        docker.image("${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}").push()
                    }
                }
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/*.jar', allowEmptyArchive: true
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
        }

        success {
            echo 'Pipeline completed successfully.'
        }

        failure {
            echo 'Pipeline failed.'
        }
    }
}