hadoop com.sun.tools.javac.Main ./src/$1.java
jar -cvf ./src/$1.jar ./src/$1*.class
mkdir build/$1
mv ./src/$1.jar build/$1/
mv ./src/$1*.class build/$1/