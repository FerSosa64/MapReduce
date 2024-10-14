1. Subir los archivos y dataset al HDFS

Primero, asegúrese de crear un directorio en el HDFS para tus datos de entrada:
hdfs dfs -mkdir -p input_dir

Luego, sube el archivo de dataset al HDFS:
hdfs dfs -put  /home/fernandodavidsosaflores/source/preprocessed_dataset.txt input_dir

Verifica que el archivo se haya subido correctamente:
hdfs dfs -ls input_dir

2. Compilación de los archivos de código

En este paso, se debe compilar los archivos Mapper, Reducer, y Runner. Asegúrese de que el compilador javac está configurado para usar las bibliotecas de Hadoop:

mkdir -p classes
javac -cp $(hadoop classpath) -d classes mapper/M_Mapper.java reducer/R_Reducer.java runner/R_Runner.java

Verificacion de compilacion
ls -R classes

3. Cerear el archivo JAR

Crear el archivo JAR
A continuación, crea un archivo manifest.txt que indique el nombre de la clase principal (en este caso, R_Runner), y luego crea el archivo JAR con el código compilado:

Contenido de manifest.txt:
main-class: hadoop_project.R_Runner

Crear el JAR:
jar cmf manifest.txt  /home/fernandodavidsosaflores/source/contador_1_palabra/hadoop_project/wc.jar -C classes .

4. Ejecutar el proyecto MapReduce

Usa el siguiente comando para ejecutar el programa MapReduce, indicando el archivo JAR, la clase Runner, el directorio de entrada y el de salida:

hadoop jar  /home/fernandodavidsosaflores/source/contador_1_palabra/hadoop_project/wc.jar hadoop_project.R_Runner input_dir output_dir

Verificacion de Salida:
hdfs dfs -ls output_dir

5. Ver los datos
hdfs dfs -cat output_dir/part-r-00000

Hacer los mismos pasos con cambio de ruta para correr el otro projecto de contador_1_pareja. La ruta depende de que proyecto se haya corrido primero.



