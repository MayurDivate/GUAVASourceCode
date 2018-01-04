# GUAVA : A GUI tool for the Analysis and Visualization of ATAC-seq data
> [![License: GPL v3](https://img.shields.io/badge/License-GPL%20v3-blue.svg)](https://github.com/MayurDivate/GUAVASourceCode/blob/master/LICENSE) 
[![Web: GUAVA](https://img.shields.io/badge/License-PDDL-brightgreen.svg)](http://ec2-52-201-246-161.compute-1.amazonaws.com/guava/)

## Quick Start
```
unzip GUAVA-master.zip
mv GUAVA-master GUAVA
cd GUAVA
```
## Installing Dependencies</h2>
We have written configure.sh script for the easy installation of dependencies.
```
sh ./configure.sh <br/>
```

If R packages are not installed successfully, use following command to install R packages.
```
Rscript lib/InstallRequiredPackages.R 
```

## GUAVA manual
[**See manual for more information**](https://github.com/MayurDivate/GUAVA/blob/master/GUAVA_Manual.pdf)


## To start GUAVA use following command

>### GUI version
```
java –jar GUAVA.jar
```
> ### For command line interface
```
$ java -jar GUAVA.jar [options]*
```
 
## Sample Data
> To download sample data [ **Click Here** ](http://ec2-52-201-246-161.compute-1.amazonaws.com/guava/)

 
## System Requirements
- Java 1.8 or latest
- Bowtie version 1.1.2
- Python version 2.7
- MACS2 version 2.1.1.20160309
- SAMtools Version: 1.3.1
- R Version: >= 3.3.0<br/>

> ## List of required R Packages
 - ChIPseeker
 -  ReactomePA
 - TxDb.Hsapiens.UCSC.hg19.knownGene
 - TxDb.Mmusculus.UCSC.mm9.knownGene
 - TxDb.Mmusculus.UCSC.mm10.knownGene
 - org.Hs.eg.db
 - org.Mm.eg.db
 - ChIPpeakAnno
 - GO.db
 - KEGG.db
 - EnsDb.Hsapiens.v75
 - Rsubread
 
