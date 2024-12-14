using System;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Effects;
using System.Windows.Threading;
//Alexandra Raluca Savu 2nd DAM

namespace Buscaminas
{

    public partial class MainWindow : Window
    {
        //Matriz para guardar si las casillas tienen mina
        private bool[,] tieneMina;
        //Matriz para guardar el numero de minas cercanas a cada casilla
        private int[,] minasCerca;
        //Numero de filas y columnas del tablero
        private int filas = 9;
        private int columnas = 9;
        private int minasRestantes = 10;
        //Variables del reloj
        private DispatcherTimer temporizador;
        private int tiempoSegundos = 0;
        //Variables de contabilizar los clicks
        private int numeroDeClicks = 0;
        //Matriz booleana para almacenar las coordenadas de las casillas ya abiertas
        private bool[,] casillasAbiertas;
        public MainWindow()
        {
            InitializeComponent(); //Cargamos y renderizamos la interfaz definida en el archivo XAML
            //Inicializamos las matrices para minas y minas cercanas
            tieneMina = new bool[filas, columnas]; 
            minasCerca = new int[filas, columnas];

            InicializarTablero(); //Llamamos al metodo que coloca las minas en el tablero
            CrearBotonesTablero();//Llamamos al metodo que genera los botones para representar las casillas del tablero

            //Inicializar el reloj
            temporizador = new DispatcherTimer();
            temporizador.Interval = TimeSpan.FromSeconds(1);//Ejecutar ccada segundo
            temporizador.Tick += Temporizador_TickTock;//Manejar el evento Tick

            casillasAbiertas = new bool[filas, columnas];//Inicializamos la matriz de casillas abiertas
        }

        //Metodo del reloj
        private void Temporizador_TickTock(object sender, EventArgs e) {
            tiempoSegundos++;
            //Actualizamos el tiempo en el textblock para que se represente con 3 numeros
            Tiempo.Text = tiempoSegundos.ToString("D3");
        }



        //Metodo para crear el tablero con minas y numeros cercanos
        private void InicializarTablero()
        {
            for (int i = 0; i < filas; i++)
            {
                for (int j = 0; j < columnas; j++)
                {
                    tieneMina[i, j] = false; //Inicializamos el tablero con 0 minas
                    minasCerca[i, j] = 0; //Como no hay minas tampoco hay numeros cercanos que nos indique la cercania a las minas
                }
            }

            //Instancia para generar coordenadas aleatorias para las minas
            Random random = new Random();
            int minasColocadas = 0;

            //Se ejecuta hasta que se haya colocado 10 minas
            while (minasColocadas < minasRestantes)
            {
                //Generamos coordenadas aleatorias
                int x = random.Next(0, filas);
                int y = random.Next(0, columnas);

                //Si la posicion no tiene mina se le coloca una y se actualizan las minas cercanas
                if (!tieneMina[x, y])
                {
                    tieneMina[x, y] = true;
                    minasColocadas++;
                    ActualizarMinasCerca(x, y);
                }
            }
        }



        //Metodo para calcular las minas cercanas a una casilla
        private int ContarMinasCercanas(int x, int y) {
            int minasCercanas = 0;

            //Recorre las casillas alrededores de (x,y)
            for (int i = -1; i<= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int newX = x + i;
                    int newY = y + j;

                    //Verificar si la casilla está dentro de los limites del tablero
                    if (newX >= 0 && newX < filas && newY >= 0 && newY < columnas){
                        //Verificar  si la casilla cercana tiene una mina
                        if (tieneMina[newX, newY]) {
                            minasCercanas++;
                        }
                }
            }
        }
            return minasCercanas;
     }
        //Metodo para actualizar la matriz de minas cercanas cuando se coloca una mina
        private void ActualizarMinasCerca(int x, int y)
        {
            //Recorremos las casillas en un area de 3x3 alrededor de la mina
            for (int i = -1; i <= 1; i++)
            { 
                for (int j = -1; j <= 1; j++)
                {
                    //Calculamos las coordenadas de cada casilla adyacente a la mina
                    int nuevax = x + i;
                    int nuevay = y + j;

                    //Validamos si la posición esta dentro de los limites del tablero
                    if (nuevax >=0 && nuevax < filas //Nos aseguramos que la fila no está fuera del tablero
                        && nuevay >= 0 && nuevay < columnas //Nos aseguramos que la columna no esté fuera del tablero
                        && !tieneMina[nuevax, nuevay])  // Evitar contar minas en casillas donde ya hay una mina
                    {
                        minasCerca[nuevax, nuevay]++; //Actualiza la matriz de minas cercanas
                    }
                }
            }
        }


        //Metodo de abrir una casilla si no tiene minas cercanas
        private void AbrirCasilla(int x, int y)
        {
            //Si la casilla esta abierta no hacer nada
            if (casillasAbiertas[x, y])
            {
                return;
            }

            //Marcar la casilla como abierta
            casillasAbiertas[x, y] = true;

            //Contar cuantas minas hay cerca
            int minasCercanas = ContarMinasCercanas(x, y);

            // Mostrar el número de minas cercanas (si hay)
             MostrarNumeroCasillas(x, y, minasCercanas);

            // Iterar sobre todos los elementos hasta encontarr el boton
            foreach (UIElement element in Tablero.Children)
            {
                if (element is Button button)
                {
                    Point coordenada = (Point)button.Tag;
                    if ((int)coordenada.X == x && (int)coordenada.Y == y)
                    {
                        //SI hay minas cercanas, mostrar el numero de minas, de lo contrario dejarlo vacio
                        button.Content = minasCercanas > 0 ? minasCercanas.ToString() : string.Empty;
                        button.FontWeight = FontWeights.Bold;
                        button.FontSize = 25;
                        //Utilizaremos nuestra propia fuente descargada en la carpeta de 'Assets', para un mejor parecido al juego original.
                        button.FontFamily = new FontFamily("pack://application:,,,/Assets/Fonts/#MINE-SWEEPER"); 

                        // Aplicar colores basados en el número
                        switch (minasCercanas)
                        {
                            case 1:
                                button.Foreground = Brushes.Blue;
                                break;
                            case 2:
                                button.Foreground = Brushes.Green;
                                break;
                            case 3:
                                button.Foreground = Brushes.Red;
                                break;
                            default:
                                button.Foreground = Brushes.Transparent;
                                break;
                        }

                      button.IsEnabled = false; // Deshabilitar el boton
                   
                    }
                }
            }
            // Si no hay minas cercanas, abrir las casillas adyacentes
            if (minasCercanas == 0)
            {
                for (int i = -1; i <= 1; i++)
                {
                    for (int j = -1; j <= 1; j++)
                    {
                        //Calculamos las nuevas coordenadas de la casilla adyacente
                        int newX = x + i;
                        int newY = y + j;

                        // Verificar que la casilla adyacente esté dentro de los límites
                        if (newX >= 0 && newX < filas && newY >= 0 && newY < columnas)
                        {
                            AbrirCasilla(newX, newY);
                        }
                    }
                }
            }

            //Comprobar si hemos ganado
            VerificarVictoria();
        }


        //Metodo para mostrar los numeros de las casillas
        private void MostrarNumeroCasillas(int x, int y, int minasCercanas) {
            foreach (UIElement element in Tablero.Children) {
                if (element is Button button) {
                    Point coordenada = (Point)button.Tag;
                    if ((int)coordenada.X == x && (int)coordenada.Y == y) {
                        button.Content = minasCercanas.ToString();
                        button.IsEnabled = false;
                    }
                }
            }
        }



        //Metodo para crear los botones en el tablero
        private void CrearBotonesTablero()
        {
            //Limpiar el tablero
            Tablero.Children.Clear();
            Tablero.RowDefinitions.Clear();
            Tablero.ColumnDefinitions.Clear();

            // Crear filas y columnas para un tablero de 9x9
            for (int i = 0; i < filas; i++)
            {
                Tablero.RowDefinitions.Add(new RowDefinition
                {
                    Height = new GridLength(1, GridUnitType.Star)
                });
            }

            for (int j = 0; j < columnas; j++)
            {
                Tablero.ColumnDefinitions.Add(new ColumnDefinition
                {
                    Width = new GridLength(1, GridUnitType.Star)
                });
            }

            for (int i = 0; i < filas; i++)
            {
                for (int j = 0; j < columnas; j++)
                {
                    //Crear un botón para cada casilla
                    Button casillaButton = new Button
                    {
                        Width = 40,
                        Height = 40,
                        Margin = new Thickness(0),
                        Background = Brushes.LightGray,
                        Tag = new Point(i, j),//Usamos el tag para guardar las coordenadas de la casilla para referencia futura

                        // Centrar el contenido
                        HorizontalContentAlignment = HorizontalAlignment.Center,
                        VerticalContentAlignment = VerticalAlignment.Center,
                        FontSize = 16,  // Aumentar el tamaño de la fuente
                        FontWeight = FontWeights.Bold,


                        //Borde de la casilla
                        BorderBrush = Brushes.Gray,
                        BorderThickness = new Thickness(3),
                        Effect = new DropShadowEffect
                        {
                            Color = Colors.Black,
                            Direction = 315,
                            ShadowDepth = 3,
                            BlurRadius = 4,
                            Opacity = 0.5
                        }
                    };


                    //Manejar click derecho para marcar minas
                    casillaButton.MouseRightButtonDown += CasillaButton_RightClick;

                    //Manejar que pasa cuando le damos click izquierdo = descubrir la casilla
                    casillaButton.Click += CasillaButton_Click;


                    //Añadir el boton al tablero/grid
                    Tablero.Children.Add(casillaButton);

                    //Establecer la posición (fila, columna) en el tablero
                    Grid.SetRow(casillaButton, i);
                    Grid.SetColumn(casillaButton, j);
                }
            }
        }




        //Metodo para decidir que pasa cuando hacemos click izquierdo
        private void CasillaButton_Click(object sender, RoutedEventArgs e)
        {
            Button button = sender as Button;//Identificar que boton disparó el evento
            if (button == null) return;

            //Incrementamos el contador
            numeroDeClicks++;

            //Inicializar el temporizador si es el primer click
            if (tiempoSegundos == 0) {
                temporizador.Start();
            }

            //Verificar si la casilla tiene una bandera, si la tiene, no hacer nada
            if (button.Content as string == "🚩")
            {
                return;
            }
            //Recuperar las coordenadas almacenadas en el boton
            Point coordenada = (Point)button.Tag;
            int fila = (int)coordenada.X;
            int columna = (int)coordenada.Y;

            //Si la casilla tiene mina, mostramos todas las minas
            if (tieneMina[fila, columna])
            {
                MostrarTodasLasMinas();
                FinalizarJuegoPerdido(fila, columna);
                return;

            }
            // Abrir la casilla seleccionada
            AbrirCasilla(fila, columna);

            // Verificar si se ha ganado
            VerificarVictoria();
        }


        //Metodo para manejar el click derecho (colocar/quitar bandera)
        private void CasillaButton_RightClick(object sender, MouseButtonEventArgs e)
        {
            Button boton = sender as Button; //Identificar que botón disparo el evento
            if (boton == null) return;

            //Si la casilla  esta marcada como mina quitar la marca al hacer click
            if (boton.Content as string == "🚩")
            {
                boton.Content = ""; //Quitar la marca 
                minasRestantes++;//Incrementar el contador de minas
            }
            else
            {
                boton.Content = "🚩"; //Marcar la casilla
                minasRestantes--;//Restarle minas al contador
            }
            //Actualizar visualmente el contador de minas en la interfaz
            ContadorMinas.Text = minasRestantes.ToString("D3");
        }




        //Metodo para mostrar todas las minas
        private void MostrarTodasLasMinas()
        {
            foreach (UIElement element in Tablero.Children)
            {
                if (element is Button boton)
                {
                    Point coordenada = (Point)boton.Tag;
                    int fila = (int)coordenada.X;
                    int columna = (int)coordenada.Y;

                    //Si la casilla tiene una mina mostrarla en rojo
                    if (tieneMina[fila, columna])
                    {
                        boton.Content = "💣"; //Representacion grafica de una mina
                        boton.Background = Brushes.Red;
                        boton.Foreground = Brushes.Black;

                    }

                }
            }
        }


        //Metodo para controlar que pasa cuando perdemos
        private void FinalizarJuegoPerdido(int x, int y)
        {
            temporizador.Stop();

            //Resaltamos la casilla con la mina que detono
            Button clickedCasilla = (Button)Tablero.Children[x * columnas + y];
            clickedCasilla.Background = Brushes.Red;
            clickedCasilla.Content = "💣";
            clickedCasilla.Foreground = Brushes.Red;

            //Cambiar el estado del boton de reinicio
            BotonReset.Content = "😵";
            BotonReset.Foreground = Brushes.Orange;

            //Deshabilitar el tablero
            foreach (UIElement element in Tablero.Children)
            {
                if (element is Button boton)
                {
                    boton.IsEnabled = false;
                }
            }
            //Mostrar mensaje de derrota
            MessageBox.Show($"¡Has perdido! La mina ha explotado\n" +
                $"Numero de clicks: {numeroDeClicks}\n" +
               $"Tiempo transcurrido: {tiempoSegundos} segundos",
               "Derrota", MessageBoxButton.OK, MessageBoxImage.Error);

            MostrarTodasLasMinas();
        }


        //Metodo para comprobar victoria
        private void VerificarVictoria()
        {
            foreach (UIElement element in Tablero.Children)
            {
                if (element is Button boton)
                {
                    Point coordenada = (Point)boton.Tag;
                    int fila = (int)coordenada.X;
                    int columna = (int)coordenada.Y;

                    // Si una casilla sin mina no está descubierta
                    if (!tieneMina[fila, columna] && boton.IsEnabled)
                    {
                        return; // No hemos ganado aún
                    }

                    // Si una casilla con mina no está marcada (asumiendo que "M" es la marca de mina)
                    if (tieneMina[fila, columna] && (boton.Content == null || boton.Content.ToString() != "🚩"))
                    {
                        return; // No hemos ganado aún
                    }
                }
            }

            //Si hemos llegado hasta aqui todas las minas están descubiertas
            FinalizarJuegoGanado();
        }


        //Metodo para procesar un juego ganado
        private void FinalizarJuegoGanado()
        {
            temporizador.Stop();
            //Cambiar el estado del boton de reinicio
            BotonReset.Content = "😎";

            //Deshabilitar el tablero
            foreach (UIElement element in Tablero.Children)
            {
                if (element is Button boton)
                {
                    boton.IsEnabled = false;
                }
            }

           //Mostrar mensaje de victoria
           MessageBox.Show($"Felicidades, has ganado a las minas\n" + 
               $"Numero de clicks: {numeroDeClicks}\n" +
               $"Tiempo transcurrido: {tiempoSegundos} segundos",
               "Victoria", MessageBoxButton.OK, MessageBoxImage.Information);
        }



        //Metodo de reinicio del juego
        private void BotonReset_Click(object sender, RoutedEventArgs e)
        {
            //Reiniciar el tablero
            tieneMina = new bool[filas, columnas];
            minasCerca = new int[filas, columnas];
            minasRestantes = 10;

            //Restablecemos el contador de minas
            ContadorMinas.Text = minasRestantes.ToString("D3");

            //Restablecer temporizador
            tiempoSegundos = 0;
            Tiempo.Text = "000";

            //Volver a habilitar el boton de reinicio
            BotonReset.Content = "🙂";

            // Reiniciar la matriz de casillas abiertas
            casillasAbiertas = new bool[filas, columnas];

            foreach (UIElement element in Tablero.Children)
            {
                if (element is Button boton)
                {
                    boton.IsEnabled = true; 
                    boton.Content = null; 
                    
                }
            }
            numeroDeClicks = 0;
            //Volver a reiniciar y crear el tablero y casillas
            InicializarTablero();
            CrearBotonesTablero();
        }
    }
}
