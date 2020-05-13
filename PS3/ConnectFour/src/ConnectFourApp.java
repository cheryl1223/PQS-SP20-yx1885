import model.ConnectFourModel;
import view.ConnectFourView;
/**
 * This is the Connect Four app main entry.
 *
 */
public class ConnectFourApp {

  public static void main(String[] args) {
    new ConnectFourApp().start();
  }
  
  private void start() {
    ConnectFourModel model = ConnectFourModel.getInstance();
    ConnectFourView view = new ConnectFourView(model);
  }
}