/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package submarine;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import submarine.conversion.SentenceDataConvertor;

/**
 *
 * @author Ondroid
 */
public class DataSentence {

    // private Map<String, Double> _sentence = new HashMap<String, Double>();
    private byte[] sentence;
    private DecimalFormat decimalFormatDouble = new DecimalFormat("#0.00");

    public DataSentence() {
        decimalFormatDouble.setRoundingMode(RoundingMode.UP);
    }

    public void setSentence(byte[] sentence) {
        this.sentence = sentence;
    }

    public byte[] getSentence() {
        return sentence;
    }

    // temperature
    public double getTemperature() {
// TO DO
        // return Double.parseDouble(_decimalFormatDouble.format(SentenceDataConvertor.getTemperature(_sentence[0], _sentence[1])));
        return SentenceDataConvertor.getTemperature(sentence[0], sentence[1]);
    }

    // azimuth
    public int getAzimuth() {
        return SentenceDataConvertor.getAzimuth(sentence[2]);
    }

    public double getLogicVoltage() {
        return SentenceDataConvertor.getLogicVoltage(sentence[4], sentence[5]);
    }

    public double getServoVoltage() {
        return SentenceDataConvertor.getServoVoltage(sentence[5], sentence[6]);
    }

    public double getDepth() {
        return SentenceDataConvertor.getDepth(sentence[8], sentence[9]);
    }

    // TODO
    public int getAccelX() {
        return SentenceDataConvertor.getAccelX(sentence[15], sentence[14]);
    }

    public int getAccelY() {
        return SentenceDataConvertor.getAccelY(sentence[17], sentence[16]);
    }

    public int getAccelZ() {
        return SentenceDataConvertor.getAccelZ(sentence[19], sentence[18]);
    }

    public double getI2C1() {
        return SentenceDataConvertor.getI2C1(sentence[8], sentence[9]);
    }

    public double getI2C2() {
        return SentenceDataConvertor.getI2C2(sentence[8], sentence[9]);
    }

    public double getI2C3() {
        return SentenceDataConvertor.getI2C3(sentence[8], sentence[9]);
    }

    public double getI2C4() {
        return SentenceDataConvertor.getI2C4(sentence[8], sentence[9]);
    }
}
