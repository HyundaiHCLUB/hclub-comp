pipeline {
    agent any

    stages {
       stage('CLONE')
        {
        steps{
           git branch : 'prod', credentialsId : 'git-token' ,url : 'https://github.com/HyndaiFinalProject/hclub-comp.git'
            sh '''
            cd /var/jenkins_home/workspace/hclub-comp
            '''
        }
           
        }
        stage('PUT application.yml'){
            steps{
                sh '''
                cd /var/jenkins_home/workspace/hclub-comp
                echo 'current dir ' ${PWD}
                cp /var/jenkins_home/config/application.yml /var/jenkins_home/workspace/hclub-comp/src/main/resources
                '''
                
            }
        }
        
        stage('Docker Build')
        {
        steps{
           
            sh '''
           echo 'Build dir' ${PWD}
           cd /var/jenkins_home/workspace/hclub-comp
           docker stop hclub-comp || true
           docker rm hclub-comp || true
           
           docker rmi popopododo/hclub-comp || true
           
           docker build -t popopododo/hclub-comp .
            '''
        }
           
        }
        
        stage('Docker Deploy')
        {
        steps{
           
            sh '''
           docker run --name hclub-comp -d -p 8082:8080 popopododo/hclub-comp
            '''
        }
           
        }
    }
   
}