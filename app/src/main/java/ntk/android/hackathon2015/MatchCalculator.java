package ntk.android.hackathon2015;

/**
 *
 * @author hna
 */
public class MatchCalculator {

    public static double[] full_calc()
    {
        double financeIndex = calFinance(25, 250, 1500, 74.12, 92.44, 66.9);
        double careerIndex = calCareer(3, 1, 2, 4, 6, 3.2, 3.1, 3.2, 2.8, 3.3);
        double generalIndex = calGeneral(20, 50, 100, 90, 51.6, 63.54, 30.5, 65.03);
        double climateIndex = calClimate(0.5, 0.5, 0.5);
        double weightedPercentage = calWeighted(financeIndex, careerIndex, generalIndex, climateIndex, 2, 1, 3, 4);
        double x[] = {careerIndex,financeIndex,generalIndex,climateIndex,weightedPercentage};
        return x;
    }
    /*****************************************************************************/

    public static double calFinance(double inputDinner, double inputGrocery, double inputRent, double restaurantApi, double groceriesApi, double rentApi)
    {
        double dinnerIndex = inputDinner - 30 * restaurantApi / 100;
        double groceryIndex = inputGrocery - 250 * groceriesApi / 100;
        double rentIndex = inputRent - 2000 * rentApi / 100;
        double calculatedIndex = 10 * dinnerIndex + 3 * groceryIndex + rentIndex;
        double maxIndex = 10 * (inputDinner / 4) + 3 * (inputGrocery / 4) + inputRent / 4;

        return calculatedIndex/maxIndex;
    } // end calFinance
    /*****************************************************************************/

    public static double calCareer(double inputOps, double inputWlb, double inputCulture, double inputLeadership, double inputImage, double opsApi, double wlbApi, double cultureApi, double leadershipApi, double overallApi)
    {
        double opsIndex = opsApi * inputOps;
        double wlbIndex = wlbApi * inputWlb;
        double cultureIndex = cultureApi * inputCulture;
        double leadershipIndex = leadershipApi * inputLeadership;
        double calculatedIndex = (opsIndex + wlbIndex + cultureIndex + leadershipIndex) / 40.25;
        double imageIndex = (overallApi * inputImage) / (4.4 * inputImage);
        return (calculatedIndex + imageIndex) / 2;
    } // end calFinance
    /*****************************************************************************/

    public static double calGeneral(double inputPollution, double inputHealthcare, double inputTraffic, double inputCrime, double pollutionApi, double healthcareApi, double trafficApi, double crimeApi)
    {
        double pollutionPor = pollutionApi / 100;
        double healthcarePor = healthcareApi / 100;
        double trafficPor = (-1 * trafficApi + 25) / 30;
        double crimePor = 2 * (100 - crimeApi) / 100;
        double weightedPollution = inputPollution * pollutionPor;
        double weightedhealthcare = inputHealthcare * healthcarePor;
        double weightedtraffic = inputTraffic * trafficPor;
        if(weightedtraffic < 0)
            weightedtraffic = 0;
        double weightedcrime = inputCrime * crimePor;
        double calculatedIndex = (weightedPollution + weightedhealthcare + weightedtraffic + weightedcrime) / (inputPollution + inputHealthcare + inputCrime);
        if(calculatedIndex > 1)
            calculatedIndex = 1;

        return calculatedIndex;
    } // end calFinance
    /*****************************************************************************/

    public static double calClimate(double inputTemp, double inputSnow, double inputRain)
    {
        double calculatedClimate = inputTemp + inputSnow + inputRain;
        return calculatedClimate / 3;
    } // end calFinance
    /*****************************************************************************/

    public static double calWeighted(double finance, double career, double general, double climate, double inputFinance, double inputCareer, double inputGeneral, double inputClimate)
    {
        double weightedFinance = (5 - inputFinance) * 0.5 * finance;
        double weightedCareer = (5 - inputCareer) * 0.5 * career;
        double weightedGeneral = (5 - inputGeneral) * 0.5 * general;
        double weightedClimate = (5 - inputClimate) * 0.5 * climate;
        double calculatedWeight = (weightedFinance + weightedCareer + weightedGeneral + weightedClimate);
        return calculatedWeight/5;
    } // end calFinance

}

