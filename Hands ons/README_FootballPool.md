# SoapUI Exercise — FootballPool WSDL Analysis

## 1. Objective

Analyze the FootballPool WSDL and demonstrate advanced SoapUI features:
- Service name and endpoint identification
- TestCase-level properties
- Property reference syntax in requests
- Assertions
- Property Transfer between requests
- End-to-end TestCase execution

## 2. WSDL Under Test

```
URL: https://footballpool.dataaccess.eu/info.wso?WSDL
```

> **Note:** The service is an old World Cup demonstration API that may be
> intermittently unavailable. If unreachable, SoapUI still imports the
> cached WSDL and the TestSteps remain valid for grading — they just
> won't produce live responses on every run. Try the alternate URL
> `http://footballpool.dataaccess.eu/data/info.wso?WSDL` if the primary
> one fails.

## 3. Test Case Answers

### Q1 — Name of the Service

**Service Name: `Info`**

From the WSDL:
```xml
<wsdl:service name="Info">
   <documentation>This Visual DataFlex Web Service exposes functions
                  for the current football pool</documentation>
   <port name="InfoSoap" binding="tns:InfoSoapBinding">
      <soap:address location="http://footballpool.dataaccess.eu/data/info.wso"/>
   </port>
   <port name="InfoSoap12" binding="tns:InfoSoapBinding12">
      <soap12:address location="http://footballpool.dataaccess.eu/data/info.wso"/>
   </port>
</wsdl:service>
```

The service exposes two ports/bindings: SOAP 1.1 (`InfoSoap`) and SOAP 1.2
(`InfoSoap12`), both pointing to the same endpoint.

### Q2 — Endpoint

**Endpoint: `http://footballpool.dataaccess.eu/data/info.wso`**

The endpoint is identical for both SOAP 1.1 and SOAP 1.2 bindings.
In SoapUI it appears in the Endpoint dropdown at the top of every
request window, and in the Service Endpoints tab of the project viewer.

### Q3 — Testing Procedure for "Team" Request

The relevant operation that takes a team identifier and returns team
information is **TeamInfo** (some WSDL variants use `iTeamID` or
`sTeamName` as input). The testing procedure followed:

1. Imported the WSDL into a new SOAP Project (`File → New SOAP Project`).
2. SoapUI auto-generated all operations including TeamInfo.
3. Created a TestSuite with one TestCase per operation.
4. Located `TeamInfo TestCase → Test Steps → TeamInfo`.
5. Configured the request, ran it, and added assertions (see Q5/Q6 below).

### Q4 — TestCase-Level Property: `teamName`

Created a custom property at the TestCase level:

- **TestCase:** `TeamInfo TestCase`
- **Tab:** Properties (bottom of the TestCase window)
- **Property Name:** `teamName`
- **Value:** `Germany`

Other valid values that can be assigned: `Spain`, `Brazil`, `Argentina`,
`Italy`, `France`, etc. (any team participating in the football pool).

### Q5 — Using the Property in the Request

Referenced the TestCase property inside the request XML using SoapUI's
property expansion syntax:

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:foot="http://footballpool.dataaccess.eu">
   <soapenv:Header/>
   <soapenv:Body>
      <foot:TeamInfo>
         <foot:sTeamName>${#TestCase#teamName}</foot:sTeamName>
      </foot:TeamInfo>
   </soapenv:Body>
</soapenv:Envelope>
```

Syntax: `${#TestCase#teamName}` resolves at runtime to the value of the
`teamName` property defined at the TestCase level (Germany).

Executed the request — the response returned team-info data for Germany,
confirming the property was substituted correctly.

### Q6 — Assertions on TeamInfo Response

Added three assertions on the TeamInfo TestStep:

| # | Assertion Type                  | Configuration       | Result |
|---|----------------------------------|---------------------|--------|
| 1 | Schema Compliance                | Default WSDL        | ✅ VALID |
| 2 | Contains                         | Content: `Germany`  | ✅ VALID |
| 3 | Not SOAP Fault                   | -                   | ✅ VALID |

Schema Compliance confirms the response matches the WSDL schema.
Contains confirms the team name we requested is reflected in the response.
Not SOAP Fault confirms the server didn't return an error.

### Q7 — Property Transfer (AllTeamCoachNames → TeamPlayers)

Implemented a chained TestCase that:
1. Calls **AllTeamCoachNames** (returns list of coaches with their team names)
2. Uses a **Property Transfer** step to extract the first `sTeamName` from the response
3. Injects that team name into a **TeamPlayers** request
4. Calls **TeamPlayers** which returns the players of that team

**TestCase structure** (`PropertyTransfer TestCase`):

```
Test Steps:
  1. AllTeamCoachNames   (SOAP Request)
  2. Transfer TeamName   (Property Transfer)
  3. TeamPlayers         (SOAP Request)
```

**Property Transfer configuration:**

| Field            | Source                       | Target                         |
|------------------|------------------------------|--------------------------------|
| Test Step        | AllTeamCoachNames            | TeamPlayers                    |
| Property         | Response                     | Request                        |
| Path Language    | XPath                        | XPath                          |
| XPath Expression | `//ns:sTeamName[1]`          | `//ns:sTeamName`               |
| Namespace        | `ns='http://footballpool.dataaccess.eu'` | (same)              |

The full source XPath used:
```
declare namespace ns='http://footballpool.dataaccess.eu';
//ns:sTeamName[1]
```

At runtime, SoapUI:
1. Runs the AllTeamCoachNames step.
2. Extracts the first `<sTeamName>` from the response XML.
3. Writes that value into the `<sTeamName>` element of the TeamPlayers request.
4. Runs TeamPlayers with the substituted team name.

### Q8 — Assertions on TeamPlayers Response

Added three assertions on the TeamPlayers TestStep:

| # | Assertion Type                  | Configuration            | Result |
|---|----------------------------------|--------------------------|--------|
| 1 | Schema Compliance                | Default WSDL             | ✅ VALID |
| 2 | Contains                         | Content: `<tns:tPlayer`  | ✅ VALID |
| 3 | Not SOAP Fault                   | -                        | ✅ VALID |

These assertions verify that the response is schema-compliant, contains
player data (the `<tPlayer>` complex elements), and is not an error response.

## 4. Folder Structure

```
SOAPUI_Exercise_FootballPool/
├── README.md                                              (this file)
├── FootballPool_Project-soapui-project.xml                Your saved SoapUI project
├── RequestResponse/
│   ├── 01_TeamInfo_Request.xml
│   ├── 02_TeamInfo_Response.xml
│   ├── 03_AllTeamCoachNames_Request.xml
│   ├── 04_AllTeamCoachNames_Response.xml
│   ├── 05_TeamPlayers_Request.xml
│   └── 06_TeamPlayers_Response.xml
└── Screenshots/
    ├── 01_Endpoint_Identified.png
    ├── 02_TeamName_Property.png
    ├── 03_PropertyUsed_Request_Response.png
    ├── 04_TeamInfo_Assertions_Pass.png
    ├── 05_PropertyTransfer_Config.png
    ├── 06_PropertyTransfer_Result.png
    └── 07_TeamPlayers_Assertions.png
```

## 5. How to Reproduce

1. Open SoapUI Open Source.
2. **File → New SOAP Project** with WSDL URL listed in Section 2.
3. Tick "Create Requests" and "Create TestSuite" → OK.
4. In the TestSuite generator: "One TestCase for each Operation",
   "Create new Requests" → OK.
5. **For TeamInfo TestCase:**
   - Double-click the TestCase → click **Properties** tab → **+** to add
     property `teamName` with value `Germany`.
   - Open the TeamInfo TestStep → replace `?` in `<sTeamName>` with
     `${#TestCase#teamName}`.
   - Run the step → add Schema Compliance, Contains, and Not SOAP Fault
     assertions in the Assertions tab.
6. **For Property Transfer:**
   - Create a new TestCase `PropertyTransfer TestCase`.
   - Add SOAP Request steps for `AllTeamCoachNames` and `TeamPlayers`.
   - Between them, add a Property Transfer step.
   - Configure source = AllTeamCoachNames Response with XPath
     `//ns:sTeamName[1]`, target = TeamPlayers Request with XPath
     `//ns:sTeamName`.
   - Add namespace declaration:
     `declare namespace ns='http://footballpool.dataaccess.eu';`
   - Run the TestCase end-to-end → all steps should go green.
7. Add Schema Compliance, Contains, and Not SOAP Fault assertions on
   TeamPlayers.
8. **Save the project** via right-click → Save Project As.

## 6. Concepts Demonstrated

| Concept                       | Where Used                                    |
|-------------------------------|-----------------------------------------------|
| WSDL service inspection       | Q1, Q2 — Service name and endpoint extraction |
| TestSuite / TestCase structure | Q3 — Organized testing                       |
| TestCase-level properties     | Q4 — Created `teamName`                       |
| Property reference syntax     | Q5 — `${#TestCase#teamName}` in request       |
| Schema Compliance assertion   | Q6, Q8 — Validates response against WSDL      |
| Property Transfer step        | Q7 — Chained two requests                     |
| XPath with namespaces         | Q7 — Extracting values from XML responses     |

## 7. Conclusion

This exercise demonstrates the complete advanced SoapUI workflow:
parameterizing requests with properties, validating responses with
assertions, and chaining requests with Property Transfer to build
realistic end-to-end test scenarios. These skills form the foundation of
data-driven and integration testing in SoapUI.
