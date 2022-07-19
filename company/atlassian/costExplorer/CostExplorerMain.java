package com.atlassian.RateLimiter;

import com.sun.tools.javac.util.Pair;
import sun.util.resources.LocaleData;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Cost Explorer should be able to provide a report of -
 *
 * Monthly cost (Generate a bill for each month, including bill amount for future months for the unit year)
 *
 * Yearly cost estimates (for the unit year)
 * CostExplorer ->
 *   monthlyCostList(): Array/List of size 12 filled with cost incurred in each month of the unit year
 *      return: List<Date, Amount>
 *   annualCost(): Total cost in a unit year
 *      return: 12*Amount
 *
 *   //  Atlassian pricing plans as tuple of {planId, monthlyCost}
 * [
 *   {BASIC, 9.99},
 *   {STANDARD, 49.99},
 *   {PREMIUM, 249.99}
 * ]
 *
 * //  Customer subscription information
 * Customer -> C1
 * Product ->
 * 	Name -> Jira
 * 	Subscription -> [ BASIC, "2022-03-10”  ] //  { planId, startDate }, date in YYYY-MM-DD format
 * 	monthlyCostList: Output: [<"2022-04-10”, 9.99>, <"2022-05-10”, 9.99>]
 * 	annualCost: <"2023-03-09”, 12*9.99>
 */

public class CostExplorerMain {

    private final int MONTHS = 12;
    SubscriptionConfig subscriptionConfig = new SubscriptionConfig();

    public HashMap<Date, Double> getMonthlyCostList(CustomerInput customerInput) {
        Map<Date, Double> monthlyCost = new HashMap<>();
        Double cost = subscriptionConfig.plan.get(customerInput.getPlanId());

        for(int i=0; i<MONTHS; i++) {
            monthlyCost.put(customerInput.getStartDate().setMonth(customerInput.getStartDate().getMonth()+(i+1)), cost);  // TODO: date
        }

        return monthlyCost;
    }

    public Pair<Date, Double> getAnnnualCost(CustomerInput customerInput) {
        Map<Date, Double> annualCostResult = new HashMap<>();
        Double cost = subscriptionConfig.plan.get(customerInput.getPlanId());
        Double annualCost = new Double(12) * cost;

        annualCostResult.put(customerInput.getStartDate().setYear(customerInput.getStartDate().getYear() + 1), annualCost);  // TODO: date

        return annualCostResult;
    }
    public static void main(String[] args) {
	// write your code here
        CustomerInput customerInput = new CustomerInput();
        customerInput.setPlanId("BASIC");
        customerInput.setStartDate(new LocaleData(2022-03-10));

        com.company.practice.RateLimiterMain mainObject = new com.company.practice.RateLimiterMain();
        HashMap<Date, Double> monthlyCost = mainObject.getMonthlyCostList(customerInput);
        for(Date date: monthlyCost.keySet()) {
            System.out.println("For Date: "+ date + " amount to be paid is: "+ monthlyCost.get(date));
        }


        HashMap<Date, Double> annualCostResult = mainObject.getAnnnualCost(customerInput);

    }
}
