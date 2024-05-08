
# Knox

#### The problem Knox solves

Knox provides a secure, cloud based code execution environment to Financial Information Users (FIU) without the need to ever access/process/store customers' financial information (FI), within their own premises. FIUs can remotely upload custom functions in JavaScript or Python, and directly execute on the FI data provided by Financial Information Providers (FIP).

We propose the addition of a new regulated entity within the AA framework, viz. the Virtual Data Room Provider (VDRP). In extension to this, we also propose new endpoint additions to the ReBIT API specifications and some miniscule enhancements to a couple of the existing AA endpoints. We have documented the proposed enhancements to data and encryption flows in the product guide on Notion, linked here.

We also propose guidelines around which regulatory and audit policies maybe formulated. We recommend (and have also implemented) a NO DATA PERSISTENCE POLICY and strict software controls around what kind of data can be returned in the output of the function execution. Knox implements and enforces a strict BOOLEAN ONLY JSON format for the output, thus ensuring no data leaks. This also in some ways solves the Data Governance problem statement to some extent because raw data is never revealed to the FIUs. Knox also ensures that the output of a function is TRANSIENT in nature.

When introduced as a new entity to the ecosystem, this will ensure trust, security, transparency and accountability by way of regulation and audits and enforcement of data persistence policies for VDRPs. This results in considerable benefits to both the FIUs and the Customers:

FIUs can offload server and resource management to the cloud and do away with the hassle of compliance with data persistence policies.
Customers are assured that FIUs do not have direct access to their data and all possible misuse is avoided. They have complete visibility into which trusted VDRPs will process their data at the time of approving/denying consent.
#### Challenges we ran into
The primary challenge that we faced while trying to address the Virtual Data Room problem statement, essentially boiled down to the constraints of the existing framework. How can a Virtual Data Room Provider access FI data, without registering as an FIU? Restricting our solution to the existing framework meant making tradeoffs which raised several red flags in our eyes from a security point of view. FIUs cannot hand over their API Keys in a secure manner to third party middleware to fetch and process data for them. If the middleware organization is registered as an FIU, they would still be governed by the same data access and persistence policies that any other FIU would have to follow. This approach, although convenient, isn't elegant.

We solved this problem by proposing the introduction of a new regulated entity to the AA framework: the VDRP who are governed by a separate set of data policies. We spent a lot of time going over existing data and encryption flows and eventually came up with an enhanced architecture that seamlessly extends from the existing framework while making absolutely minimal changes to the existing API specs.

## Showcase

 - This Project was one of the winners of [Account Aggregator Hackathon 2020](https://aa-hackathon.devfolio.co/)
 - [Winning List of Projects](https://sahamati.org.in/account-aggregator-hackathon-2020-projects-list/)
 - [Project Hosted on DevFolio](https://devfolio.co/projects/knox)


## Tech Stack

**Client:** React, Redux, MUI

**Server:** Spring boot, Hibernate, Apache Open Whisk, Kubernetes


## License

[![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](https://choosealicense.com/licenses/mit/)


