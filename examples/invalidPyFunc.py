import json;

def main(args):
    dict = json.loads(args);
    accounts = dict["accounts"];
    transactions = accounts[0]["transactions"];
    noOfTxn = len(transactions)
    totalBalance = 0;
    for transaction in transactions:
        totalBalance += float(transaction["CurrentBalance"]);
    avgBalance = totalBalance/noOfTxn;
    result = avgBalance > 50000;
    resultDict = {
        "avgBalance": avgBalance,
        "greaterThan50K": result
    };
    return json.dumps(result);