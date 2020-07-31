import json

def main(args):
    accounts = args["accounts"]
    transactions = accounts[0]["transactions"]
    noOfTxn = len(transactions)
    totalBalance = 0
    for transaction in transactions:
        totalBalance += float(transaction["CurrentBalance"])
    avgBalance = totalBalance/noOfTxn
    result = "true" if avgBalance < 50000 else "false"
    return { "greaterThan50K": result }