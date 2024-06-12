import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
public class WeatherAppGui  extends JFrame {

    private JSONObject weatherData;
    public WeatherAppGui(){
        super("Weather App ");

        //configure the GUI to end once if the close icon is pressed
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setSize(450,700);

        setLocationRelativeTo(null);

        setLayout(null);

        setResizable(false);

        addGuiComponents();

    }

    private void addGuiComponents(){
        JTextField searchField = new JTextField();
        searchField.setBounds(15,15,340,40);

        searchField.setFont(new Font("Century Gothic", Font.ITALIC,20));

        add(searchField);

        JLabel weatherConditionImage = new JLabel(loadImage("src/asserts/rainy2.png"));
        weatherConditionImage.setBounds(20,90,400,240);
        add(weatherConditionImage);

        JLabel temperatureText = new JLabel("10 C");
        temperatureText.setBounds(0,320,450,64);
        temperatureText.setFont(new Font("Century Gothic", Font.BOLD,50));

        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);

        add(temperatureText);

        JLabel weatherConditionDesc = new JLabel("Cloudy");
        weatherConditionDesc.setBounds(0,390,450,46);
        weatherConditionDesc.setFont(new Font("Century Gothic", Font.BOLD,40));
        weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionDesc);

        JLabel humidity = new JLabel(loadImage("src/asserts/humidity2.png"));

        humidity.setBounds(0,480,120,150);
        add(humidity);


        JLabel humidityText = new JLabel();
        humidityText.setText("<html><h2 style='color:red;'>Humidity<br>100%</h2></html>");
        humidityText.setBounds(105,535,100,50);
        humidityText.setFont(new Font("Century Gothic", Font.ITALIC,20));

        add(humidityText);

        JLabel windSpeed = new JLabel(loadImage("src/asserts/windspeed2.png"));

        windSpeed .setBounds(200,480,150,150);
        add(windSpeed);


        JLabel windSpeedText = new JLabel();
        windSpeedText.setText("<html><b>WindSpeed</b><br>15km/h</html>");
        windSpeedText.setBounds(330,535,100,50);
        windSpeedText.setFont(new Font("Century Gothic", Font.ITALIC,20));

        add(windSpeedText);

        JButton searchButton = new JButton(loadImage("src/asserts/search2.png"));

        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        searchButton.setBounds(370,15,40,40);

        searchButton.setBackground(Color.lightGray);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = searchField.getText();

                if(userInput.replaceAll("\\s","").length()<=0)
                {
                    return;
                }

                weatherData = WeatherApp.getWeatherData(userInput);


                String weatherCondition = (String) weatherData.get("weather_condition");

                switch (weatherCondition)
                {
                    case "Clear":
                        weatherConditionImage.setIcon(loadImage("./src/asserts/clear2.png"));
                        break;
                    case "Cloudy":
                        weatherConditionImage.setIcon(loadImage("./src/asserts/cloudy2.png"));
                        break;
                    case "Rain":
                        weatherConditionImage.setIcon(loadImage("./src/asserts/rainy2.png"));
                        break;
                    case "Snow":
                        weatherConditionImage.setIcon(loadImage("./src/asserts/snow2.png"));
                        break;
                }

                double temperature = (double)weatherData.get("temperature");
                temperatureText.setText(temperature +" C");

                weatherConditionDesc.setText(weatherCondition);

                long humidity = (long) weatherData.get("humidity");
                humidityText.setText("<html><b>Humidity</b><br>"+humidity+"%</html>");

                double windspeed = (double) weatherData.get("windspeed");
                windSpeedText.setText("<html><b>WindSpeed</b><br>"+windspeed+"km/hr</html>");
            }
        });

        add(searchButton);


    }

    private ImageIcon loadImage(String path)
    {
        try{
            BufferedImage image = ImageIO.read(new File(path));

            return new ImageIcon(image);
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("Could not find resource!!!");

        return null;
    }


}
