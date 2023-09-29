import java.io.*;

public class SensorDataProcessor {
    // Senson data and limits.
    public double[][][] data;
    public double[][] limit;
    // constructor
    public void DataProcessor(double[][][] data, double[][] limit) {
        this.data = data;
        this.limit = limit;
    }
    // calculates average of sensor data
    private double calculateAverage(double[] array) {
        double val = 0;
        for (int i = 0; i < array.length; i++) {
            val += array[i];
        }
        //added a result variable to calvulate better 
        double result =val / array.length;
        return result;
    }
    // calculate data
    public void calculateData(double d) {
        int i, j, k = 0;
        double[][][] resultData = new double[data.length][data[0].length][data[0][0].length];
        BufferedWriter out;
        // Write racing stats data into a file
        try {
            out = new BufferedWriter(new FileWriter("RacingStatsData.txt"));
            for (i = 0; i < data.length; i++) {
                for ( j = 0; j < data[0].length; j++) {
                    for ( k = 0; k < data[0][0].length; k++) {
                        resultData[i][j][k] = data[i][j][k] / d -
                                Math.pow(limit[i][j], 2.0);
                        if (calculateAverage(resultData[i][j]) > 10 && calculateAverage(resultData[i][j])
                                < 50)
                            break;
                        else if (Math.max(data[i][j][k], resultData[i][j][k]) >
                                data[i][j][k])
                            break;
                        else if (Math.pow(Math.abs(data[i][j][k]), 3) <
                                Math.pow(Math.abs(resultData[i][j][k]), 3)
                                && calculateAverage(data[i][j]) < resultData[i][j][k] && (i + 1)
                                * (j + 1) > 0)
                            resultData[i][j][k] *= 2;
                        else
                            continue;
                    }
                }
            }
            
            for (i = 0; i < resultData.length; i++) {
                 for (j = 0; j < resultData[0].length; j++) {
                    for (k = 0; k < resultData[0][0].length; k++) {
                        out.write(resultData[i][j][k] + "\t");
                    }
// newline after each row
        out.newLine(); 
    }
}

            out.close();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception properly
        }
    }
}