
var TestTemplate = {
    "ver": "1.0",
    "FI": [
      {
        "data": [
          {
            "linkRefNumber": "XXXX-XXXX-XXXX",
            "maskedAccNumber": "XXXXXXXX4020",
            "encryptedFI": {
              "accounts": [
                {
                  "profile": {
                    "Account": {
                      "number": "XXXXXXXX3329",
                      "acctype": "SAVINGS",
                      "fitype": "DEPOSIT"
                    },
                    "Holders": {
                      "Holder": [
                        {
                          "name": "Akshay Kumar",
                          "Dob": "15081947",
                          "mobile": "8008233551",
                          "Nominee": "REGISTERED",
                          "landline": "",
                          "address": "8/1190, 5th Cross, 3rd Main, 7th Block, Jayanagar, Bangalore - 560011",
                          "email": "akshayku@gmail.com",
                          "PAN": "AIMPN5746M",
                          "ckycCompliance": "true"
                        }
                      ],
                      "type": "SINGLE"
                    }
                  },
                  "summary": {
                    "CurrentBalance": "101666.33",
                    "Currency": "INR",
                    "ExchangeRate": " ",
                    "balanceDateTime": "2020-06-22T13:20:00+05:30",
                    "Type": "SAVINGS",
                    "branch": "Jayanagar 4th Block",
                    "Facility": "OD",
                    "ifsc": "ICIC0001124",
                    "micrCode": "500240246",
                    "openingDate": "2004-08-06",
                    "CurrentODLimit": "0",
                    "DrawingLimt": "0",
                    "Status": "Active",
                    "Pending": {
                      "TransactionType": "Debit",
                      "Amount": "0"
                    }
                  },
                  "transactions": [
                    {
                      "Type": "DEBIT",
                      "Mode": "UPI",
                      "Amount": "1239",
                      "CurrentBalance": "62289.25",
                      "TransactionTimestamp": "2019-12-19T13:20:14+05:30",
                      "valueDate": "2019-12-19",
                      "Txnid": "M3258741",
                      "Narration": "UPI/935314560764/getsimpl/simpl@axisbank/Axis Bank",
                      "Reference": "RFN00013383"
                    },
                    {
                      "Type": "DEBIT",
                      "Mode": "UPI",
                      "Amount": "16",
                      "CurrentBalance": "62073.25",
                      "TransactionTimestamp": "2019-12-20T18:56:12+05:30",
                      "valueDate": "2019-12-20",
                      "Txnid": "M3917183",
                      "Narration": "UPI/935418465214/On tapping Pay/AMZN0002966107@/St",
                      "Reference": "RFN00038195"
                    },
                    {
                      "Type": "DEBIT",
                      "Mode": "UPI",
                      "Amount": "1432",
                      "CurrentBalance": "60641.25",
                      "TransactionTimestamp": "2019-12-21T09:14:47+05:30",
                      "valueDate": "2019-12-21",
                      "Txnid": "M4246404",
                      "Narration": "UPI/935511799557/On tapping Pay/ola.money1@axis/Ax",
                      "Reference": "RFN00050601"
                    },
                    {
                      "Type": "DEBIT",
                      "Mode": "OTHERS",
                      "Amount": "710.5",
                      "CurrentBalance": "45595.49",
                      "TransactionTimestamp": "2019-12-21T16:09:04+05:30",
                      "valueDate": "2019-12-21",
                      "Txnid": "M5563288",
                      "Narration": "VIN/BBBP Bengal/201912220356/935522425140/ ",
                      "Reference": "RFN00100225"
                    },
                    {
                      "Type": "DEBIT",
                      "Mode": "CARD",
                      "Amount": "4101",
                      "CurrentBalance": "41494.49",
                      "TransactionTimestamp": "2019-12-22T10:14:59+05:30",
                      "valueDate": "2019-12-22",
                      "Txnid": "M5892509",
                      "Narration": "BIL/ONL/001876585007/State Bank/SBICARD_RICI833/SB",
                      "Reference": "RFN00112631"
                    },
                    {
                      "Type": "DEBIT",
                      "Mode": "OTHERS",
                      "Amount": "200",
                      "CurrentBalance": "41294.49",
                      "TransactionTimestamp": "2019-12-22T10:18:12+05:30",
                      "valueDate": "2019-12-22",
                      "Txnid": "M6221730",
                      "Narration": "GIB/000044233050/STAX /63900042212201900058 ",
                      "Reference": "RFN00125037"
                    },
                    {
                      "Type": "DEBIT",
                      "Mode": "ATM",
                      "Amount": "10000",
                      "CurrentBalance": "31294.49",
                      "TransactionTimestamp": "2019-12-25T11:34:44+05:30",
                      "valueDate": "2019-12-25",
                      "Txnid": "M6550951",
                      "Narration": "ATM/SECNQ998/CASH WDL/25-12-19 ",
                      "Reference": "RFN00137443"
                    },
                    {
                      "Type": "DEBIT",
                      "Mode": "FT",
                      "Amount": "2138",
                      "CurrentBalance": "29156.49",
                      "TransactionTimestamp": "2019-12-30T08:31:47+05:30",
                      "valueDate": "2019-12-30",
                      "Txnid": "M6880172",
                      "Narration": "ACH/TP Kotak Life Ins/1791288292 ",
                      "Reference": "RFN00149849"
                    },
                    {
                      "Type": "DEBIT",
                      "Mode": "FT",
                      "Amount": "330",
                      "CurrentBalance": "28826.49",
                      "TransactionTimestamp": "2019-12-30T09:56:44+05:30",
                      "valueDate": "2019-12-30",
                      "Txnid": "M7209393",
                      "Narration": "VIN/SWIGGY /201912301322/936407439400/ ",
                      "Reference": "RFN00162255"
                    },
                    {
                      "Type": "CREDIT",
                      "Mode": "OTHERS",
                      "Amount": "518",
                      "CurrentBalance": "27228.49",
                      "TransactionTimestamp": "2019-12-30T13:15:04+05:30",
                      "valueDate": "2019-12-30",
                      "Txnid": "M7867835",
                      "Narration": "000501523329:Int.Pd:30-09-2019 to 30-12-2019 ",
                      "Reference": "RFN00187067"
                    },
                    {
                      "Type": "DEBIT",
                      "Mode": "FT",
                      "Amount": "2097.44",
                      "CurrentBalance": "24769.05",
                      "TransactionTimestamp": "2020-01-01T16:36:47+05:30",
                      "valueDate": "2020-01-01",
                      "Txnid": "M8526277",
                      "Narration": "VIN/IRCTC /202001012059/000115145689/ ",
                      "Reference": "RFN00211879"
                    },
                    {
                      "Type": "DEBIT",
                      "Mode": "FT",
                      "Amount": "2024.55",
                      "CurrentBalance": "22576.5",
                      "TransactionTimestamp": "2020-01-03T10:52:51+05:30",
                      "valueDate": "2020-01-03",
                      "Txnid": "M9513940",
                      "Narration": "ACH/TP ExideLife/INGLife/1793513439 ",
                      "Reference": "RFN00249097"
                    },
                    {
                      "Type": "DEBIT",
                      "Mode": "CARD",
                      "Amount": "1297",
                      "CurrentBalance": "21193.5",
                      "TransactionTimestamp": "2020-01-05T01:13:17+05:30",
                      "valueDate": "2020-01-05",
                      "Txnid": "M10501603",
                      "Narration": "VPS/SWATHI HOSP/202001051605/000510633940/BANGALOR",
                      "Reference": "RFN00286315"
                    },
                    {
                      "Type": "DEBIT",
                      "Mode": "CASH",
                      "Amount": "5000",
                      "CurrentBalance": "16176.5",
                      "TransactionTimestamp": "2020-01-05T02:02:25+05:30",
                      "valueDate": "2020-01-05",
                      "Txnid": "M11160045",
                      "Narration": "NFS/KBL18213/CASH WDL/05-01-20 ",
                      "Reference": "RFN00311127"
                    },
                    {
                      "Type": "DEBIT",
                      "Mode": "FT",
                      "Amount": "5000",
                      "CurrentBalance": "11176.5",
                      "TransactionTimestamp": "2020-01-06T23:11:18+05:30",
                      "valueDate": "2020-01-06",
                      "Txnid": "M11489266",
                      "Narration": "BIL/BPAY/001888932102/BSE ISIP/BSE000000034260 ",
                      "Reference": "RFN00323533"
                    },
                    {
                      "Type": "DEBIT",
                      "Mode": "FT",
                      "Amount": "5000",
                      "CurrentBalance": "6176.5",
                      "TransactionTimestamp": "2020-01-07T06:22:27+05:30",
                      "valueDate": "2020-01-07",
                      "Txnid": "M11818487",
                      "Narration": "BIL/BPAY/001889944243/BSE ISIP/BSE000000034260 ",
                      "Reference": "RFN00335939"
                    },
                    {
                      "Type": "DEBIT",
                      "Mode": "FT",
                      "Amount": "5000",
                      "CurrentBalance": "1176.5",
                      "TransactionTimestamp": "2020-01-07T07:23:34+05:30",
                      "valueDate": "2020-01-07",
                      "Txnid": "M12147708",
                      "Narration": "BIL/BPAY/001889918950/MOTILAL OS/MODIRECT-B02925 ",
                      "Reference": "RFN00348345"
                    },
                    {
                      "Type": "DEBIT",
                      "Mode": "FT",
                      "Amount": "3062",
                      "CurrentBalance": "3114.5",
                      "TransactionTimestamp": "2020-01-07T20:25:32+05:30",
                      "valueDate": "2020-01-07",
                      "Txnid": "M12806150",
                      "Narration": "CMS/000599599606/AD_LIC__648652702 ",
                      "Reference": "RFN00373157"
                    },
                    {
                      "Type": "DEBIT",
                      "Mode": "FT",
                      "Amount": "464",
                      "CurrentBalance": "31916.5",
                      "TransactionTimestamp": "2020-01-09T06:53:11+05:30",
                      "valueDate": "2020-01-09",
                      "Txnid": "M14452255",
                      "Narration": "VIN/DISH TV IND/202001091516/000909100480/ ",
                      "Reference": "RFN00435187"
                    },
                    {
                      "Type": "CREDIT",
                      "Mode": "FT",
                      "Amount": "50000",
                      "CurrentBalance": "81916.5",
                      "TransactionTimestamp": "2020-01-09T07:01:17+05:30",
                      "valueDate": "2020-01-09",
                      "Txnid": "M14781476",
                      "Narration": "BIL/INFT/001892335260/ ",
                      "Reference": "RFN00447593"
                    },
                    {
                      "Type": "DEBIT",
                      "Mode": "FT",
                      "Amount": "30000",
                      "CurrentBalance": "51916.5",
                      "TransactionTimestamp": "2020-01-10T08:11:17+05:30",
                      "valueDate": "2020-01-10",
                      "Txnid": "M15110697",
                      "Narration": "ACH/HDFCLTD/245261499 ",
                      "Reference": "RFN00459999"
                    }
                  ]
                }
              ]
            }
          }
        ],
        "fipID": "AA-14",
        "KeyMaterial": {
          "Nonce": "R4s6vNI7I/JfdeA3/6dMMQ==",
          "curve": "Curve25519",
          "DHPublicKey": {
            "Parameters": "Some Params",
            "KeyValue": "683938505ec529a700fcceab66273d1aa78d494208a4769930f0818872159265",
            "expiry": "2020-12-06T11:39:57.153Z"
          },
          "Signature": "jFJcYCOTVV6iiLPlM7qY+Zz+3PF8oUPFg1byb1GNr+k=",
          "cryptoAlg": "ECDHE",
          "params": "Some Params"
        }
      }
    ],
    "sessionID": "",
    "timestamp": "",
    "txnid": ""
  }

  export default TestTemplate;