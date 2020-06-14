import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Utils {
    private Utils() {
    }

    public static URL getLocation(String name, Class<?> clazz) throws URISyntaxException, MalformedURLException {
        return clazz.getResource("main.fxml").toURI().toURL();
    }

    public static String formatDate(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return simpleDateFormat.format(date);
    }
}
