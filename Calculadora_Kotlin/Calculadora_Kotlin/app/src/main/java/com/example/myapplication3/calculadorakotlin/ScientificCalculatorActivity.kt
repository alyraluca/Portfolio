package com.example.myapplication3.calculadorakotlin

//Importamos las classes necesarias
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.text.Editable
import android.util.Log
import kotlin.math.*
import android.view.Menu
//Classe para manejar las operaciones de la calculadora
import net.objecthunter.exp4j.ExpressionBuilder
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.Toast
import com.example.myapplication3.calculadorakotlin.databinding.ActivityScientificCalculatorBinding

//La classe ScientificCalculatorActivity hereda de AppCompatActivity
class ScientificCalculatorActivity : AppCompatActivity() {

    // Declaración de una propiedad para almacenar la referencia a la vista generada por View Binding
    private lateinit var binding: ActivityScientificCalculatorBinding

    //Declaramos propiedades privadas para los elementos de la interfaz de usuario
    private lateinit var scientificEditText: EditText
    private lateinit var operacionesEditText: EditText
    private lateinit var resultadosEditText: EditText

    //El metodo onCreate se llama cuando se crea la actividad por primera vez
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Establecemos la vista de contenido en el diseño definido en activity_scientific_calculator.xml
        binding = ActivityScientificCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Inicializamos los elementos de la interfaz de usuario
        scientificEditText = findViewById(R.id.operaciones)
        operacionesEditText = findViewById(R.id.operaciones)
        resultadosEditText = findViewById(R.id.resultados)

        // Mensajes de registro y Toast para indicar que la actividad se ha creado
        Log.i("Ciclo de vida", "ScientificCalculatorActivity - onCreate")
        Toast.makeText(this, "ScientificCalculatorActivity - onCreate", Toast.LENGTH_SHORT).show()

    }
    // Nuevas funciones para manejar las acciones del menú
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.popup_menu, menu)
        return true
    }
    //Función para escoger las opciones del menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_how_to_use -> {
                showHowToUse()
                return true
            }
            R.id.menu_about -> {
                showAbout()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    // Funcion para abrir la parte de 'Como utilizar' del menu
    private fun showHowToUse() {
        // Implement the logic to show instructions or a video for how to use the app
        val intent = Intent(this, HowToUseVideoActivity::class.java)
        startActivity(intent)
        Toast.makeText(this, "Como útilizar...", Toast.LENGTH_SHORT).show()
    }

    // Implementar la lógica para mostrar información sobre la aplicación/creador
    private fun showAbout() {
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
        Toast.makeText(this, "Acerca de...", Toast.LENGTH_SHORT).show()
    }

    // El método onStart se llama cuando la actividad se vuelve visible para el usuario
    override fun onStart() {
        super.onStart()

        // Recuperar la última expresión almacenada en SharedPreferences
        val preferences = getSharedPreferences("Resultados", Context.MODE_PRIVATE)
        val ultimaExpresion  = preferences.getString("ultimaExpresion","")

        // Puedes mostrar la última expresión en tu actividad, por ejemplo, en un EditText
        operacionesEditText.text = Editable.Factory.getInstance().newEditable(ultimaExpresion)

        Log.i("Ciclo de vida", "ScientificCalculatorActivity - onStart")
        Toast.makeText(this, "ScientificCalculatorActivity - onStart", Toast.LENGTH_SHORT).show()
    }
    // El método onDestroy se llama antes de que la actividad sea destruida
    override fun onDestroy() {
        super.onDestroy()
        Log.i("Ciclo de vida", "ScientificCalculatorActivity - onDestroy")
        Toast.makeText(this, "ScientificCalculatorActivity - onDestroy", Toast.LENGTH_SHORT).show()
    }


    //Función para manejar los clics en botones de digitos y operadores
    fun operationAction(view: View) {
        if (view is Button) {
            // Convertimos la vista a un botón
            val button = view as Button
            // Obtenemos el texto actual de operacionesEditText
            val currentText = operacionesEditText.text.toString()
            // Actualizamos operacionesEditText con el texto del botón clickeado
            operacionesEditText.text =
                Editable.Factory.getInstance().newEditable(currentText + button.text)
        } else if (view is ImageButton) {
            // Convertimos la vista a un ImageButton
            val button = view as ImageButton
            // Obtenemos el texto actual de operacionesEditText
            val currentText = operacionesEditText.text.toString()
            // Actualizamos operacionesEditText con el contenido de la descripción del ImageButton
            operacionesEditText.text =
                Editable.Factory.getInstance().newEditable(currentText + button.contentDescription)
        }
    }
    //Función para manejar clics en botones de numeros
    fun numberAction(view: View) {
        if (view is ImageButton) {
            val button = view as ImageButton
            val currentText = operacionesEditText.text.toString()
            operacionesEditText.text =
                Editable.Factory.getInstance().newEditable(currentText + button.contentDescription)
        }
    }
    //Función para borrar/resetear las operaciones y los resultados
    fun allClearAction(view: View) {
        operacionesEditText.text.clear()
        resultadosEditText.text.clear()

    }


    //Función para evaluar las expresiones matemáticas
    private fun evaluarExpresion(expression: String): Double {
        try {
            //Usamos ExpressionBuilder de la biblioteca exp4j para construir y evaluar las expresiones
            val resultado = ExpressionBuilder(expression)
                .build()
                .evaluate()
            // Redondeamos los resultaados a dos decimales para mayor claridad
            return "%.2f".format(resultado).toDouble()
        } catch (e: Exception) {
            //Manejo de excepciones a causa de expresiones inválidas
            throw IllegalArgumentException("Expresión inválida")
        }

    }
    //Función para manejar el boton de "="
    //Evalua las expresiones y muestra el resultado
    fun equalsAction(view: View) {
        val expression = operacionesEditText.text.toString()
        try {
            //Evalúa la expressión usando la función evaluarExpresion y lo guarda en "val result"
            val result = evaluarExpresion(expression)

            // Muestra solo el resultado en el campo de operaciones
            operacionesEditText.text = Editable.Factory.getInstance().newEditable(result.toString())

            //Muestra el resultado (lo tenemos que convertir desde tipo String a Editable)
            resultadosEditText.text = Editable.Factory.getInstance().newEditable(result.toString())

            // Guardar la expresión en SharedPreferences
            guardarExpresion(expression)


        } catch (e: Exception) {
            //Manejo de excepciones a causa de expresiones inválidas.
            resultadosEditText.text = Editable.Factory.getInstance().newEditable("Error")
        }
    }
    // Función para guardar la expresión en SharedPreferences
    private fun guardarExpresion(expresion: String) {
        val preferences = getSharedPreferences("Resultados", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString("ultimaExpresion", expresion)
        editor.apply()
    }
    // Función para guardar el resultado en SharedPreferences
    private fun guardarResultado(expresion: String) {
        val preferences = getSharedPreferences("Resultados", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString("ultimaExpresion", expresion)
        editor.apply()
    }
    // Método para manejar la acción del botón de función exponencial
    fun exponentialAction(view: View) {
        val currentText = scientificEditText.text.toString()
        scientificEditText.text =
            Editable.Factory.getInstance().newEditable(currentText + "^")
    }

    // Método para manejar la acción del botón de signo menos
    fun minusSignAction(view: View) {
        val currentText = scientificEditText.text.toString()
        //Comprobar si el numero empieza con '-'
        if (currentText.startsWith("-")) {
            // Si empieza con '-' lo quita
            scientificEditText.text =
                Editable.Factory.getInstance().newEditable(currentText.substring(1))
        } else {
            // Si no empieza con '-' se lo pone
            scientificEditText.text = Editable.Factory.getInstance().newEditable("-$currentText")
        }
    }
    // Función para manejar el botón de SIN (seno)
    fun sinOperation(view: View) {
        val expression = scientificEditText.text.toString()
        try {
            val result = sin(Math.toRadians(evaluarExpresion(expression)))
            resultadosEditText.text = Editable.Factory.getInstance().newEditable(result.toString())
        } catch (e: Exception) {
            resultadosEditText.text = Editable.Factory.getInstance().newEditable("Error")
        }
    }

    // Función para manejar el botón de COS (coseno)
    fun cosOperation(view: View) {
        val expression = scientificEditText.text.toString()
        try {
            val result = cos(Math.toRadians(evaluarExpresion(expression)))
            resultadosEditText.text = Editable.Factory.getInstance().newEditable(result.toString())
        } catch (e: Exception) {
            resultadosEditText.text = Editable.Factory.getInstance().newEditable("Error")
        }
    }

    // Función para manejar el botón de TAN (tangente)
    fun tanOperation(view: View) {
        val expression = scientificEditText.text.toString()
        try {
            val result = tan(Math.toRadians(evaluarExpresion(expression)))
            resultadosEditText.text = Editable.Factory.getInstance().newEditable(result.toString())
        } catch (e: Exception) {
            resultadosEditText.text = Editable.Factory.getInstance().newEditable("Error")
        }
    }

    // Función para manejar el botón de LOG (logaritmo)
    fun logOperation(view: View) {
        val expression = scientificEditText.text.toString()
        try {
            val result = log10(evaluarExpresion(expression))
            resultadosEditText.text = Editable.Factory.getInstance().newEditable(result.toString())
        } catch (e: Exception) {
            resultadosEditText.text = Editable.Factory.getInstance().newEditable("Error")
        }
    }

    // Función para manejar el botón de IN (logaritmo natural)
    fun inOperation(view: View) {
        val expression = scientificEditText.text.toString()
        try {
            val result = ln(evaluarExpresion(expression))
            resultadosEditText.text = Editable.Factory.getInstance().newEditable(result.toString())
        } catch (e: Exception) {
            resultadosEditText.text = Editable.Factory.getInstance().newEditable("Error")
        }
    }
    // Función para manejar el botón de PI
    fun piOperation(view: View) {
        val currentText = scientificEditText.text.toString()
        scientificEditText.text =
            Editable.Factory.getInstance().newEditable(currentText + PI)
    }

    // Función para manejar el botón de raíz cuadrada
    fun squareOperation(view: View) {
        val expression = scientificEditText.text.toString()
        try {
            val result = sqrt(evaluarExpresion(expression))
            resultadosEditText.text = Editable.Factory.getInstance().newEditable(result.toString())
        } catch (e: Exception) {
            resultadosEditText.text = Editable.Factory.getInstance().newEditable("Error")
        }
    }

    // Función para manejar el botón de porcentaje
    fun percentageOperation(view: View) {
        val expression = scientificEditText.text.toString()
        try {
            val result = evaluarExpresion(expression) / 100.0
            resultadosEditText.text = Editable.Factory.getInstance().newEditable(result.toString())
        } catch (e: Exception) {
            resultadosEditText.text = Editable.Factory.getInstance().newEditable("Error")
        }
    }

    fun openCalculator(view: View) {
        // Obtén la expresión actual antes de cambiar de actividad
        val expression = operacionesEditText.text.toString()
        // Guardar la expresión en SharedPreferences antes de cambiar de actividad
        guardarResultado(expression)

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}