# Proyecto MapReduce: Proceso Completo

Este es el proceso completo para subir los archivos al HDFS, compilar el código, crear el archivo JAR, ejecutar el proyecto MapReduce y verificar los resultados, todo en un solo flujo.

---

## Pasos

### 1. Subir los archivos y dataset al HDFS

Primero, asegúrate de crear un directorio en el HDFS para tus datos de entrada:

```bash
hdfs dfs -mkdir -p input_dir
```

Luego, sube el archivo de dataset al HDFS:

```bash
hdfs dfs -put  /home/fernandodavidsosaflores/source/preprocessed_dataset.txt input_dir
```

Verifica que el archivo se haya subido correctamente:
```bash
hdfs dfs -ls input_dir
```

### 2. Compilacion de los archivos de codigo

En este paso, se debe compilar los archivos Mapper, Reducer, y Runner. Asegúrese de que el compilador javac está configurado para usar las bibliotecas de Hadoop:
```bash
mkdir -p classes
javac -cp $(hadoop classpath) -d classes mapper/M_Mapper.java reducer/R_Reducer.java runner/R_Runner.java
```

Verificacion de compilacion
```bash
ls -R classes
```

### 3. Crear el archivo JAR

Crear el archivo JAR
A continuación, crea un archivo manifest.txt que indique el nombre de la clase principal (en este caso, R_Runner), y luego crea el archivo JAR con el código compilado:

Contenido de manifest.txt:
```bash
main-class: hadoop_project.R_Runner
```

Crear el JAR:
```bash
jar cmf manifest.txt  /home/fernandodavidsosaflores/source/contador_1_palabra/hadoop_project/wc.jar -C classes .
```

### 4. Ejecutar el proyecto MapReduce

Usa el siguiente comando para ejecutar el programa MapReduce, indicando el archivo JAR, la clase Runner, el directorio de entrada y el de salida:
```bash
hadoop jar  /home/fernandodavidsosaflores/source/contador_1_palabra/hadoop_project/wc.jar hadoop_project.R_Runner input_dir output_dir
```
Verificacion de Salida:
```bash
hdfs dfs -ls output_dir
```
### 5. Ver los datos
```bash
hdfs dfs -cat output_dir/part-r-00000
```
---
Hacer los mismos pasos con cambio de ruta para correr el otro projecto de contador_1_pareja. La ruta depende de que proyecto se haya corrido primero.

