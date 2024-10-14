import nltk
from nltk.corpus import stopwords
from nltk.tokenize import word_tokenize, sent_tokenize

# Asegurarse de que los recursos de NLTK están disponibles
nltk.download('stopwords')
nltk.download('punkt')

def preprocess_text(input_file, output_file):
    # Leer el archivo de entrada con codificación UTF-8
    with open(input_file, 'r', encoding='utf-8') as infile:
        text = infile.read()
    
    # Tokenización de las oraciones y palabras
    sentences = sent_tokenize(text)
    words = word_tokenize(text.lower())

    # Eliminación de palabras basura
    stop_words = set(stopwords.words('english'))
    filtered_words = [word for word in words if word.isalpha() and word not in stop_words]

    # Guardar las palabras filtradas en el archivo de salida
    with open(output_file, 'w', encoding='utf-8') as outfile:
        outfile.write(' '.join(filtered_words))

# Definir archivos de entrada y salida
input_file = "archivo.txt"
output_file = "preprocessed_dataset.txt"

# Ejecutar la función de preprocesamiento
preprocess_text(input_file, output_file)
