# [Ariadne](https://wala.github.io/ariadne/)

This is the top level repository for Ariadne code.  More information on using the Ariadne tools can be found [here](https://wala.github.io/ariadne/).  This repository is code to analyze machine learning code with WALA.  Currently, the code consists of the analysis of Python (com.ibm.wala.cast.python), analysis focused on machine learning in Python (com.ibm.wala.cast.python.ml), support for using the analysis via J2EE Websockets (com.ibm.wala.cast.python.ml.j2ee) and their associated test projects.

# Build

1. Build Jython3

   ```
   git clone https://github.com/juliandolby/jython3
   cd jython3
   ant
   ```

   Copy `jython-dev.jar` to `cast-python3/libs/jython-dev.jar`

2. Build Ariadne

   ( I think the build process of the upstream is a mess. So I had to reconstruct a new project called "python-frontend". However, I only reconstructed a few part of them )

   ```
   cd python-frontend
   mvn clean package
   ```

   Code demos are written as test case in `src/test/java/com/ibm/wala/cast/python/test`.

# IDE

```bash
git clone https://github.com/Anemone95/IDE.git
gradle publishToMavenLocal # mvn is deprecated
```



