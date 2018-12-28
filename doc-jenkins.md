<div id="table-of-contents">
<h2>Table of Contents</h2>
<div id="text-table-of-contents">
<ul>
<li><a href="#sec-1">1. installation</a>
<ul>
<li><a href="#sec-1-1">1.1. installer java</a></li>
<li><a href="#sec-1-2">1.2. installer jenkins</a></li>
<li><a href="#sec-1-3">1.3. configurer le firewall</a></li>
<li><a href="#sec-1-4">1.4. setting up jenkins</a></li>
</ul>
</li>
<li><a href="#sec-2">2. administration</a>
<ul>
<li><a href="#sec-2-1">2.1. plugins</a></li>
<li><a href="#sec-2-2">2.2. installer pipeline maven integration</a></li>
<li><a href="#sec-2-3">2.3. configuration globale des outils</a></li>
</ul>
</li>
<li><a href="#sec-3">3. jobs</a>
<ul>
<li><a href="#sec-3-1">3.1. créer un nouveau job</a></li>
<li><a href="#sec-3-2">3.2. pipeline-syntax</a></li>
</ul>
</li>
</ul>
</div>
</div>

cf [documentation](https://linuxize.com/post/how-to-install-jenkins-on-centos-7/)

# installation<a id="sec-1" name="sec-1"></a>

## installer java<a id="sec-1-1" name="sec-1-1"></a>

    $ sudo yum install java

vérifer la version:

    $ java -version

sortie standard:

    openjdk version "1.8.0_191"
    OpenJDK Runtime Environment (build 1.8.0_191-b12)
    OpenJDK 64-Bit Server VM (build 25.191-b12, mixed mode)

## installer jenkins<a id="sec-1-2" name="sec-1-2"></a>

    $ curl --silent --location http://pkg.jenkins-ci.org/redhat-stable/jenkins.repo | sudo tee /etc/yum.repos.d/jenkins.repo

    $ sudo rpm --import https://jenkins-ci.org/redhat/jenkins-ci.org.key

    $ sudo yum install jenkins

    $ sudo systemctl start jenkins

    $ sudo systemctl status jenkins

    $ sudo systemctl enable jenkins

## configurer le firewall<a id="sec-1-3" name="sec-1-3"></a>

    sudo firewall-cmd --permanent --zone=public --add-port=8080/tcp

    sudo firewall-cmd --reload

## setting up jenkins<a id="sec-1-4" name="sec-1-4"></a>

# administration<a id="sec-2" name="sec-2"></a>

## plugins<a id="sec-2-1" name="sec-2-1"></a>

    http://<server_ip>:8080/pluginManager/

où <server<sub>ip</sub>> est l'adresse de votre serveur jenkins

    http://54.38.180.120:8080/pluginManager/

exemples: chercher *python* et *php*, *wordpress*, *ruby*, etc

## installer pipeline maven integration<a id="sec-2-2" name="sec-2-2"></a>

chercher le **plugin** dans l'onglet *disponible*

## configuration globale des outils<a id="sec-2-3" name="sec-2-3"></a>

-   jdk &#x2013;> nécessite la crétion d'un compte oracle

# jobs<a id="sec-3" name="sec-3"></a>

## créer un nouveau job<a id="sec-3-1" name="sec-3-1"></a>

pipeline
-   *sauver*
-   *back to dashboard*
-   *sélectionner un projet*
-   pipeline syntax

[online doc](https://jenkins.io/doc/book/pipeline/) section "Scripted Pipeline fundamentals"
copier (et modifier) l'exemple:

    node {  
        stage('Build') { 
           echo('helloooo oueurld!')
           sh 'pwd'
        }
        stage('Test') { 
            // 
        }
        stage('Deploy') { 
            // 
        }
    }

aller dans *configure*, onglet *Advanced Project Options*, et (modifer/)coller le **Pipeline script**: 

## pipeline-syntax<a id="sec-3-2" name="sec-3-2"></a>

+créer un nouveau projet test2
-   aller dans *pipeline syntax*
-   générer le *pipeline script* avec l'URL git du projet

dans configure, onglet *Advanced Project Options*, écrire le script 
-   lancer le **build**
-   regarder les logs

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
