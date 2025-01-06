//Importacao de bibliotecas
import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

//Declaracao da classe
public class GestaoReservas
{
  //Declaracao do metodo principal
  public static void main(String[] args) throws IOException
  {
    menu();
  }

  //MENU
  public static void menu() throws IOException
  {
    //Declaracao de variaveis
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    char tipoReserva, extra, op;
    int numero, diaEntrada, diaSaida, qtdPessoas = 0, qtdE = 0, qtdC = 0, qtdP = 0, contE = 0, contC = 0 ,contP = 0, contEv = 0, contEp = 0, menorNum = 0;
    float valPagar, valExtra, desconto, imposto, taxa, acumE = 0, acumC = 0, acumP = 0, menorVal = 20000f, acumGeral = 0, acumDesc = 0;
    final float IMPOSTO = 10/100f, TAXA = 3/100f, DESCONTO = 23/100f;

    //Ciclo de repeticao do menu
    do
    {
      System.out.println("\n    ****  MENU  ****");
      System.out.println("a) Receber os dados da reserva do cliente");
      System.out.println("b) Quantidade de reservas por cada tipo");
      System.out.println("c) Tipo de reserva com mais ocupacao");
      System.out.println("d) Extra menos solicitado");
      System.out.println("e) Valor Total de desconto");
      System.out.println("f) Valor total obtido por cada tipo de reserva");
      System.out.println("g) Valor total obtido pelo lodge");
      System.out.println("h) Dados dos Programadores");
      System.out.println("i) Numero de Telefone e o valor a pagar da menor reserva");
      System.out.println("j) Sair do programa");

      //Receber a opcao
      System.out.print("\nEscolha uma opcao(a-j): ");
      op = br.readLine().charAt(0);

      switch(op)
      {
        //Opcao a) do menu
        case 'a': case 'A':
                  //Receber o numero de telefone
                  System.out.print("\nIntroduza o numero de telefone: ");
                  numero = validarInt(820000000, 879999999);

                  //Receber o tipo de reserva
                  System.out.print("\nIntroduza o tipo de reserva (E-Empresa, C-Casal, P-Particular): ");
                  tipoReserva = validarChar('E', 'e', 'C', 'c', 'P', 'p');

                  //Receber o dia de entrada
                  System.out.print("\nIntroduza o dia de entrada (1-31): ");
                  diaEntrada = validarInt(1, 31);

                  //Receber o dia de saida
                  System.out.print("\nIntroduza o dia de saida ("+diaEntrada+"-31): ");
                  diaSaida = validarInt(diaEntrada, 31);

                  //Receber e contar o numero de pessoas
                  switch(tipoReserva)
                  {
                    case 'E': case 'e':
                              System.out.print("\nIntroduza o numero de pessoas(15-100): ");
                              qtdPessoas = validarInt(15, 100);
                              qtdE += qtdPessoas;
                              contE++; 
                              break;
                    case 'C': case 'c':
                              System.out.print("\nIntroduza o numero de casais(1-50): ");
                              qtdPessoas = validarInt(1, 50);
                              qtdC += qtdPessoas * 2;
                              contC++;
                              break;
                    case 'P': case 'p':
                              System.out.print("\nIntroduza o numero de pessoas(1-100): ");
                              qtdPessoas = validarInt(1, 100);
                              qtdP += qtdPessoas;
                              contP++;
                              break;
                  }

                  //Receber o extra e calcular o valor do extra
                  System.out.println("\nEscolha um extra (V-Vista a praia, P-Piscina, N-Nenhuma das opcoes): ");
                  extra = validarChar('V', 'v', 'P', 'p', 'N', 'n');
                  valExtra = calcularExtra(op);

                  //Contar o tipo de extra excolhido
                  switch(extra)
                  {
                    case 'V': case 'v':
                              contEv++;
                              break;

                    case 'P': case 'p':
                              contEp++;
                              break;
                  }

                  //Calcular o valor a pagar inicial e acrescentar o valor de extra
                  valPagar = calcularValPagarInicial(tipoReserva, qtdPessoas);
                  valPagar = calcularValComAcrescimo(valPagar, valExtra); 

                  //Calcular imposto ou taxa sobre o valor a pagar
                  switch(tipoReserva)
                  {
                    case 'E': case 'e':
                              imposto = calcularPercentagem(valPagar, IMPOSTO);
                              valPagar = calcularValComAcrescimo(valPagar, imposto);
                              break;

                    case 'C': case 'c':
                              taxa = calcularPercentagem(valPagar, TAXA);
                              valPagar = calcularValComAcrescimo(valPagar, taxa);
                              break;

                    case 'P': case 'p':
                              taxa = calcularPercentagem(valPagar, TAXA);
                              valPagar = calcularValComAcrescimo(valPagar, taxa);
                              break;
                  }

                  //Calcular desconto se tempo de estadia for mais que 10 dias
                  if ((diaSaida - diaEntrada) > 10)
                  {
                    desconto = calcularPercentagem(valPagar, DESCONTO);
                    valPagar = calcularValComDecrescimo(valPagar, desconto);
                    acumDesc += desconto;
                  }

                  //Acumular o valor a pagar do tipo de reserva
                  switch(tipoReserva)
                  {
                    case 'E': case 'e':
                              acumE += valPagar;
                              break;

                    case 'C': case 'c':
                              acumC += valPagar;
                              break;

                    case 'P': case 'p':
                              acumP += valPagar;
                              break;
                  }     
                  
                  //Controlar e guardar o numero e o valor da menor reserva
                  if (menorVal > valPagar)
                  {
                    menorVal = valPagar;
                    menorNum = numero;
                  }

                  //Acumular o valor a pagar
                  acumGeral += valPagar;

                  //Visualizar o valor a pagar
                  visualizarValPagar(valPagar, numero, tipoReserva);
                  break;

        //Opcao b) do Menu
        case 'b': case 'B':
                  //Visualizar a quantidade de reservas por cada tipo
                  visualizarQtdReservasCadaTipo(contE, contC, contP);
                  break;

        //Opcao c) do Menu
        case 'c': case 'C':
                  //Visualizar o tipo de reserva com maior ocupacao
                  visualizarTipoReservaFav(qtdE, qtdC, qtdP);
                  break;

        //Opcao d) do Menu
        case 'd': case 'D':
                  //Visualizar o extra menos solicitado
                  visualizarExtraMenosSolicitado(contEv, contEp);
                  break;

        //Opcao e) do Menu
        case 'e': case 'E':
                  //Visualizar o valor total de desconto
                  visualizarValTotalDesconto(acumDesc);
                  break;

        //Opcao f) do Menu
        case 'f': case 'F':
                  //Visualizar o valor total de cada tipo de reserva
                  visualizarValorTotalCadaTipo(acumE, acumC, acumP);
                  break;

        //Opcao g) do Menu
        case 'g': case 'G':
                  //Visualizar a valor total obtido pelo lodge
                  visualizarValTotal(acumGeral);
                  break;

        //Opcao h) do Menu
        case 'h': case 'H':
                  //Visualizar os dados dos programadores
                  visualizarDadosProgramadores();
                  break;

        //Opcao i) do Menu
        case 'i': case 'I':
                  //Visualizar o numero de telefone e o valor da menor reserva
                  visualizarMenorReserva(menorNum, menorVal);
                  break;

        //Opcao j) do Menu
        case 'j': case 'J':
                  //Mensagem de despedida
                  System.out.println("Volte Sempre!");
                  break;
        default:  System.out.println("Opcao Invalida!");
                  break;
      }
    } while (op != 'j' && op != 'J');
  }

  //Metodo para validar o numero de telefone, o dia de entrada, o dia de saida e a quantidade de pessoas
  public static int validarInt(int a, int b) throws IOException
  {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int n;
    do
    {
      n = Integer.parseInt(br.readLine());

      if (n < a || n > b)
      {
        System.out.println("Erro!");
        System.out.print(": ");
      }
    } while (n < a || n > b);
    return n;
  }

  //Metodo para validar o tipo de reserva e o extra
  public static char validarChar(char a, char b, char c, char d, char e, char f) throws IOException
  {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    char x;

    do
    {
      x = br.readLine().charAt(0);

      if(x != a && x != b && x != c && x != d && x != e && x != f)
      {
        System.out.println("Erro!");
        System.out.print(": ");
      }
    }while(x != a && x != b && x != c && x != d && x != e && x != f);
    return x;
  }

  //Metodo para calcular o valor a pagar inicial (USD)
  public static float calcularValPagarInicial(char tr, int np)
  {
    final int EMPRESA = 100, CASAL = 80, PARTICULAR = 40;
    float v = 0;

    switch(tr)
    {
      case 'E': case 'e':
                v = EMPRESA * np;
                break;

      case 'C': case 'c':
                v = CASAL * np;
                break;

      case 'P': case 'p':
                v = PARTICULAR * np;
                break;
    }
    return v;
  }

  //Metodo para calcular o valor do extra (USD)
  public static float calcularExtra(char e)
  {
    final float VISTA = 50f, PISCINA = 35f;
    switch(e)
    {
      case 'V': case 'v':
                return VISTA;
      case 'P': case 'p':
                return PISCINA;
      default:
                return 0f;
    }
  }

  //Metodo para calcular o valor a pagar com extra, valor a pagar com imposto e valor com taxa(USD)
  public static float calcularValComAcrescimo(float v, float a)
  {
    return(v + a);
  }

  //Metodo para calcular o valor a pagar com desconto (USD)
  public static float calcularValComDecrescimo(float v, float d)
  {
    return(v - d);
  }

  //Metodo para calcular valor de imposto, taxa e desconto
  public static float calcularPercentagem(float v, final float P)
  {
    return(v*P);
  }

  //Metodo para converter de USD para MTN
  public static float converterUsdParaMtn(float usd)
  {
    final float USD = 65f;
    return (usd*USD);
  }

  //Metodo para visualizar o valor a pagar
  public static void visualizarValPagar(float v, int nr, char tr)
  {
    DecimalFormat mt = new DecimalFormat("###,###,###.00 MTn");
    NumberFormat usd = NumberFormat.getCurrencyInstance(Locale.US);
    
    System.out.println("\n ========================================================================================");
    System.out.println("|    Telefone    | Tipo de Reserva |   Valor a Pagar (USD)    |   Valor a Pagar (MTn)    |");
    System.out.println(" ========================================================================================");
    System.out.print("|  +258"+nr+" ");

    switch(tr)
    {
      case 'E': case 'e':
                System.out.print("|     Empresa     ");
                break;

      case 'C': case 'c':
                System.out.print("|      Casal      ");
                break;

      case 'P': case 'p':
                System.out.print("|    Particular   ");
                break;
    }

    System.out.printf("| %24s | %24s |\n", usd.format(v), mt.format(converterUsdParaMtn(v)));
    System.out.println(" ========================================================================================");
  }

  //Metodo para visualizar o tipo de reserva com maior ocupacao de pessoas
  public static void visualizarTipoReservaFav(int qE, int qC, int qP)
  {
    if (qE == qC && qC == qP)
      System.out.println("\nOs tres tipos de reserva (Empresa, Casal e Particular) for igualmente ocupados com "+qE+" pessoas cada!");
    else
    {
      if (qE == qC && qC > qP)
        System.out.println("\nEmpresa e Casal foram os tipos de reserva mais ocupados com "+qE+" pessoas cada!");
      else
      {
        if (qC == qP && qP > qE)
          System.out.println("\nCasal e Particular foram os tipos de reserva mais ocupados com "+qC+" pessoas cada!");
        else
        {
          if (qP == qE && qE > qC)
            System.out.println("\nEmpresa e Particular foram os tipos de reserva mais ocupados com "+qP+" pessoas cada!");
          else
          {
            if (qE > qC && qE > qP)
              System.out.println("\nEmpresa foi o tipo de reserva mais ocupado com "+qE+" pessoas!");
            else
            {
              if (qC > qP && qC > qE)
                System.out.println("\nCasal foi o tipo de reserva mais ocupado com "+qC+" pessoas!");
              else
                System.out.println("\nParticular foi o tipo de reserva mais ocupado com "+qP+" pessoas!");
            }
          }
        }
      }
    }
  }

  //Metodo para visualizar o extra menos solicitado
  public static void visualizarExtraMenosSolicitado(int cV, int cP)
  {
    if (cV == cP)
      System.out.println("\nA vista a praia e a piscina foram igualmente solicitados com "+cV+" socilitacoes cada!");
    else
    {
      if (cV < cP)
        System.out.println("\n A vista a praia foi o extra menos solicitado com "+cV+" socilitacoes!"); 
      else
        System.out.println("\n A piscina foi o extra menos solicitado com "+cP+" socilitacoes!"); 
    }
  }

  //Metodo para visualizar o valor total de desconto
  public static void visualizarValTotalDesconto(float aD)
  {
    DecimalFormat mt = new DecimalFormat("###,###,###.00 MTn");
    NumberFormat usd = NumberFormat.getCurrencyInstance(Locale.US);

    System.out.println("\n =====================================================");
    System.out.println("|               Valor Total de Desconto               |");
    System.out.println(" =====================================================");
    System.out.println("|       Dollar (USD)       |       Metical (MTn)      |");
    System.out.println(" =====================================================");
    System.out.printf("| %24s | %24s |\n", usd.format(aD), mt.format(converterUsdParaMtn(aD)));
    System.out.println(" =====================================================");
  }

  //Metodo para visualizar os dados dos programadores
  public static void visualizarDadosProgramadores()
  {
    System.out.println("\n =======================================");
    System.out.println("|      Nome       | Codigo de Estudante |");
    System.out.println(" =======================================");
    System.out.println("| Antonio Junior  |      20240931       |");
    System.out.println(" =======================================");
    System.out.println("| Joaquim Manjama |      20240972       |");
    System.out.println(" =======================================");
    System.out.println("| Keesha Ossumane |      20240374       |");
    System.out.println(" =======================================");
  }

  //Metodo para visualizar a quantidade de reservas feitas por cada tipo de reserva em forma de tabela    
  public static void visualizarQtdReservasCadaTipo(int cE, int cC, int cP)
  {
    DecimalFormat mt = new DecimalFormat("###,###,###.00 MTn");
    NumberFormat usd = NumberFormat.getCurrencyInstance(Locale.US);

    System.out.println("\n** TABELA DE DADOS DOS TIPOS DE RESERVA **");
    System.out.println(" ======================================");
    System.out.println("| Tipo de Reserva | Numero de Reservas |");
    System.out.println(" ======================================");
    System.out.printf("|     Empresa     | %18d |\n",cE);
    System.out.println(" ======================================");
    System.out.printf("|      Casal      | %18d |\n",cC);
    System.out.println(" ======================================");
    System.out.printf("|    Particular   | %18d |\n",cP);
    System.out.println(" ======================================");
  }

  //Metodo para visualizar o valor total obtido por cada tipo de reserva em forma de tabela    
  public static void visualizarValorTotalCadaTipo(float aE, float aC, float aP)
  {
    DecimalFormat mt = new DecimalFormat("###,###,###.00 MTn");
    NumberFormat usd = NumberFormat.getCurrencyInstance(Locale.US);

    System.out.println("\n             **** VALOR TOTAL DE CADA TIPO DE RESERVA ****");
    System.out.println(" =======================================================================");
    System.out.println("| Tipo de Reserva | Valor Total Obtido (USD) | Valor Total Obtido (MTn) |");
    System.out.println(" =======================================================================");
    System.out.printf("|     Empresa     | %24s | %24s |\n",usd.format(aE), mt.format(converterUsdParaMtn(aE)));
    System.out.println(" =======================================================================");
    System.out.printf("|      Casal      | %24s | %24s |\n",usd.format(aC), mt.format(converterUsdParaMtn(aC)));
    System.out.println(" ========================================================================");
    System.out.printf("|    Particular   | %24s | %24s |\n",usd.format(aP), mt.format(converterUsdParaMtn(aP)));
    System.out.println(" ========================================================================");
  }

  //Metodo Para visualizar o valor total obtido pelo lodge
  public static void visualizarValTotal(float v)
  {
    DecimalFormat mt = new DecimalFormat("###,###,###.00 MTn");
    NumberFormat usd = NumberFormat.getCurrencyInstance(Locale.US);

    System.out.println("\n =====================================================");
    System.out.println("|              Valor Total Obtido pelo Lodge          |");
    System.out.println(" =====================================================");
    System.out.println("|       Dollar (USD)       |       Metical (MTn)      |");
    System.out.println(" =====================================================");
    System.out.printf("| %24s | %24s |\n", usd.format(v), mt.format(converterUsdParaMtn(v)));
    System.out.println(" ======================================================");
  }

  //Metodo para visualizar o numero de telefone e o valor a pagar da menos reserva
  public static void visualizarMenorReserva(int n, float v)
  {
    DecimalFormat mt = new DecimalFormat("###,###,###.00 MTn");
    NumberFormat usd = NumberFormat.getCurrencyInstance(Locale.US);

    System.out.println("\n                    **** MENOR RESERVA ****");
    System.out.println(" ===========================================================================");
    System.out.println("| Numero de telefone |    Valor a Pagar (USD)   |    Valor a Pagar (MTn)    |");
    System.out.println(" ===========================================================================");
    System.out.print("|   +258"+n+"    ");
    System.out.printf("| %24s | %24s |\n",usd.format(v), mt.format(converterUsdParaMtn(v)));
    System.out.println(" ===========================================================================");
  }
}