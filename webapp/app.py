from flask import Flask, jsonify
from pymongo import MongoClient

app = Flask(__name__)

# Conexión a la base de datos MongoDB
client = MongoClient("mongodb://mongo:27017/herencias")  # Cambia localhost a mongo (nombre del contenedor en docker-compose)
db = client['herencias']  # Nombre de la base de datos

# Ruta para obtener todos los datos de la colección de Festividades
@app.route('/festividades', methods=['GET'])
def get_festividades():
    festividades = db['Festividades'].find()  # Obtiene todos los documentos de la colección 'Festividades'
    result = []
    
    for festividad in festividades:
        indigenous_festivities = festividad.get('indigenous_festivities', [])
        for item in indigenous_festivities:  # Itera sobre el array 'indigenous_festivities'
            result.append({
                'Nombre_Original': item.get('Nombre_Original', 'N/A'),
                'Fecha': item.get('Fecha', 'N/A'),
                'Actividades': item.get('Actividades', 'N/A'),
                'Quien_Puede_Asistir': item.get('Quien_Puede_Asistir', 'N/A'),
                'Implicaciones': item.get('Implicaciones', 'N/A')
            })
    
    return jsonify(result)

# Ruta para obtener todos los datos de la colección de Ingredientes
@app.route('/ingredientes', methods=['GET'])
def get_ingredients():
    """Obtiene todos los ingredientes de la base de datos"""
    ingredients_doc = db['Ingredientes'].find_one()  # Accede al documento con los ingredientes
    result = []
    if ingredients_doc and 'ingredients' in ingredients_doc:
        for ingredient in ingredients_doc['ingredients']:
            result.append({
                'name_spanish': ingredient.get('name_spanish', 'N/A'),
                'names_indigenous_languages': ingredient.get('names_indigenous_languages', {}),
                'production_location': ingredient.get('production_location', 'N/A'),
                'exists_today': ingredient.get('exists_today', False),
                'consumption_by_group': ingredient.get('consumption_by_group', {})
            })
    
    return jsonify(result)


# Ruta para obtener todos los datos de la colección de Poblaciones
@app.route('/poblaciones', methods=['GET'])
def get_poblaciones():
    """Obtiene todos los datos de las poblaciones indígenas"""
    poblaciones_doc = db['Poblaciones'].find_one()  # Encuentra un único documento que contiene el array 'indigenous_groups'
    
    result = []
    if poblaciones_doc and 'indigenous_groups' in poblaciones_doc:
        for poblacion in poblaciones_doc['indigenous_groups']:  # Itera sobre el array 'indigenous_groups'
            result.append({
                'nombre': poblacion.get('name', 'N/A'),
                'ubicacion': poblacion.get('location', {}),
                'poblacion': poblacion.get('population', {}),
                'lenguas_habladas': poblacion.get('languages_spoken', []),
                'estructura_social': poblacion.get('social_structure', {})
            })
    
    return jsonify(result)


# Ruta para obtener todos los datos de la colección de Recetas
@app.route('/recetas', methods=['GET'])
def get_recetas():
    """Obtiene todas las recetas indígenas de la base de datos"""
    recetas_doc = db['Recetas'].find_one()  # Encuentra un único documento que contiene el array 'indigenous_recipes'
    
    result = []
    if recetas_doc and 'indigenous_recipes' in recetas_doc:
        for receta in recetas_doc['indigenous_recipes']:  # Itera sobre el array 'indigenous_recipes'
            ingredients = []
            for ingredient in receta.get('ingredients', []):  # Itera sobre los ingredientes de cada receta
                ingredients.append({
                    'name_spanish': ingredient.get('name_spanish', 'N/A'),
                    'names_indigenous_languages': ingredient.get('names_indigenous_languages', {}),
                    'quantity': ingredient.get('quantity', 'N/A')
                })
            
            result.append({
                'recipe_name': receta.get('recipe_name', 'N/A'),
                'ingredients': ingredients,
                'preparation_steps': receta.get('preparation_steps', []),
                'preparation_time': receta.get('preparation_time', 'N/A'),
                'occasion': receta.get('occasion', 'N/A'),
                'who_prepares': receta.get('who_prepares', 'N/A'),
                'used_for_festivities': receta.get('used_for_festivities', False)
            })
    
    return jsonify(result)


# Ruta para obtener todos los datos de la colección de Usuarios
@app.route('/usuarios', methods=['GET'])
def get_usuarios():
    usuarios = db['Usuarios'].find()
    result = []
    for usuario in usuarios:
        result.append({
            'Cedula': usuario.get('Cedula', 'N/A'),
            'Nombre': usuario.get('Nombre', 'N/A'),
            'Contraseña': usuario.get('Contraseña', 'N/A'),
            'Tipo': usuario.get('Tipo', 'N/A')
        })
    return jsonify(result)

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
