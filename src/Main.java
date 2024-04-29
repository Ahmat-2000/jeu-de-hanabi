import model.Game;
import view.ViewConsole;

/**
 * Main
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("\n\n"+ """
            █████   █████                                █████      ███ 
            ░░███   ░░███                                ░░███      ░░░  
             ░███    ░███   ██████   ████████    ██████   ░███████  ████ 
             ░███████████  ░░░░░███ ░░███░░███  ░░░░░███  ░███░░███░░███ 
             ░███░░░░░███   ███████  ░███ ░███   ███████  ░███ ░███ ░███ 
             ░███    ░███  ███░░███  ░███ ░███  ███░░███  ░███ ░███ ░███ 
             █████   █████░░████████ ████ █████░░████████ ████████  █████
            ░░░░░   ░░░░░  ░░░░░░░░ ░░░░ ░░░░░  ░░░░░░░░ ░░░░░░░░  ░░░░░ """+"\n\n");
        Game game = new Game(2);
        ViewConsole viewConsole = new ViewConsole(game);
        viewConsole.startGame();
        
    }
}