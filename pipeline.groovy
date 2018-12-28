node {
    stage('Checkout') {
        git branch: 'develop',url: 'https://github.com/virtus1er/ajc-jenkins.git'
    }
    
    stage('build') {
        withMaven( maven: 'mav', jdk: 'jdk8') {
            sh "mvn clean verify"
        }
        step([$class: 'JUnitResultArchiver', testResults: 'target/surefire-reports/*.xml'])
	step([$class: 'JUnitResultArchiver', testResults: 'target/failsafe-reports/*.xml'])
	
    }
}
