package com.example.myapplication3.calculadorakotlin

//Importamos las classes necesarias
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.text.Editable
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import com.example.myapplication3.calculadorakotlin.databinding.ActivityMainBinding
//Classe para manejar las operaciones de la calculadora
import net.objecthunter.exp4j.ExpressionBuilder


//La classe MainActivity hereda de AppCompatActivity
public class MainActivity : AppCompatActivity() {

    // Declaración de una propiedad para almacenar la referencia a la vista generada por View Binding
    private lateinit var binding: ActivityMainBinding

    //Declaramos propiedades privadas para los elementos de la interfaz de usuario
    private lateinit var operacionesEditText: EditText
    private lateinit var resultadosEditText: EditText

    //El metodo onCreate se llama cuando se crea la actividad por primera vez
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Establecemos la vista de contenido en el diseño definido en acivity_main.xml
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Inicializamos los elementos de la interfaz de usuario
        operacionesEditText = findViewById(R.id.operaciones)
        resultadosEditText = findViewById(R.id.resultados)

    }
    //Función para enseñar las opciones del menu
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
        val intent = Intent(this, HowToUseVideoActivity::class.java)
        startActivity(intent)
        Toast.makeText(this, "Como útilizar...", Toast.LENGTH_SHORT).show()
    }
    // Funcion para abrir la parte de 'Sobre la aplicación' del menu
    private fun showAbout() {
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
        Toast.makeText(this, "Acerca de...", Toast.LENGTH_SHORT).show()
    }

    // El método onStart se llama cuando la actividad se vuelve visible para el usuario
    override fun onStart() {
        super.onStart()

        // Recuperar el resultado almacenado en SharedPreferences
        val preferences = getSharedPreferences("Resultados", Context.MODE_PRIVATE)
        val ultimaExpresion  = preferences.getString("ultimaExpresion","")

        // Puedes mostrar el resultado en tu actividad, por ejemplo, en un TextView
        operacionesEditText.text = Editable.Factory.getInstance().newEditable(ultimaExpresion)


        // Mensajes de registro y Toast para indicar que la actividad está empezando
        Log.i("Ciclo de vida", "MainActivity - onStart")
        Toast.makeText(this, "MainActivity - onStart", Toast.LENGTH_SHORT).show()
    }
    // El método onDestroy se llama antes de que la actividad sea destruida
    override fun onDestroy() {
        super.onDestroy()
        // Mensajes de registro y Toast para indicar que la actividad está siendo destruida
        Log.i("Ciclo de vida", "MainActivity - onDestroy")
        Toast.makeText(this, "MainActivity - onDestroy", Toast.LENGTH_SHORT).show()
    }

    //Función para manejar los clics en botones de digitos y operadores
    fun operationAction(view: View) {
        if (view is ImageButton) {
            val button = view as ImageButton
            val currentText = operacionesEditText.text.toString()
            // Utilizar la propiedad "text" al actualizar el EditText
            operacionesEditText.text = Editable.Factory.getInstance().newEditable(currentText + button.contentDescription)
        }
    }
    //Función para manejar clics en botones de numeros
    fun numberAction(view: View) {
        if (view is ImageButton) {
            val button = view as ImageButton
            val currentText = operacionesEditText.text.toString()
            operacionesEditText.text =
                Editable.Factory.getInstance().newEditable(currentText + button.contentDescription)
        } else if (view is Button) {
            val button = view as Button
            val currentText = operacionesEditText.text.toString()
            operacionesEditText.text =
                Editable.Factory.getInstance().newEditable(currentText + button.text)
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

    // Función para guardar el resultado en SharedPreferences
    private fun guardarResultado(expresion: String) {
        val preferences = getSharedPreferences("Resultados", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString("ultimaExpresion", expresion)
        editor.apply()
    }

    // Función para guardar la expresión en SharedPreferences
    private fun guardarExpresion(expresion: String) {
        val preferences = getSharedPreferences("Resultados", Context.MODE_PRIVATE)
        val editor = preferences.edit()

        editor.putString("ultimaExpresion", expresion)
        editor.apply()
    }

    //Función para abrir la calculadora scientifica
    fun openScientificCalculator(view: View) {
        // Obtén la expresión actual antes de cambiar de actividad
        val expression = operacionesEditText.text.toString()

        // Guardar la expresión en SharedPreferences antes de cambiar de actividad
        guardarResultado(expression)
        val intent = Intent(this, ScientificCalculatorActivity::class.java)
        startActivity(intent)
        finish()
    }
    // Método para manejar la acción del botón de signo menos
    fun minusSignAction(view: View) {
        val currentText = operacionesEditText.text.toString()
        //Comprobar si el numero empieza con '-'
        if (currentText.startsWith("-")) {
            // Si empieza con '-' lo quita
            operacionesEditText.text =
                Editable.Factory.getInstance().newEditable(currentText.substring(1))
        } else {
            // Si no empieza con '-' se lo pone
            operacionesEditText.text = Editable.Factory.getInstance().newEditable("-$currentText")
        }
    }
}

