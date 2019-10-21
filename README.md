# R2RByAssertions Project

## Quick start

### Prerequisites

The project has the following minimal requirements:

1. [Java 1.8](https://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html) 

2. [Eclipse](https://helpx.adobe.com/experience-manager/6-5/sites/developing/using/aem-eclipse.html) or other IDE
	
### Structure

  
The structure ``R2RByAssertions`` package is like this: ::

 	src/main/java
 	|-- com.br.ufc.arida.r2rbyassertions.main
 		|--	R2rByAsseertions.java
 			
 	src/main/resources
		|-- data
			|-- input
				|-- MyMusic_target.owl
				|-- Dbpedia_source.owl
				|-- ontologydbpedia.ttl
				|-- vocab_target.txt
			|-- output
				|-- assertions.txt
				|-- r2rMappings.ttl
  	|-- pom.xml
 	 `-- README.md

### Clone and Execute


#### Clone and Import the R2RByAssertions Project:

1. Clone the Github repository of project

     `git clone https://github.com/sssantos2/R2RByAssertions`

2. Import the AEM Site Project into Eclipse 

	1. In Eclipse, choose File > Import

	2. In the Import Dialog, choose Maven > Existing Maven Projects, then click "Next".

    3. Enter the path to your project's top-level folder, then click "Select All" and "Finish".
    


#### How to build
 Execute: ::
 
 src/main/java
 	|-- com.br.ufc.arida.r2rbyassertions.main
 		|--	R2rByAsseertions.java

  Run As > Java Application

## Reference

 [RBA: R2R By Assertions] (http://www.repositorio.ufc.br/bitstream/riufc/23530/4/2017_dis_tsvinuto.pdf).