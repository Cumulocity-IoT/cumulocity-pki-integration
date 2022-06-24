# Use Cases

This document decribes in short all the Use Cases which needs to be created for this project.

## UC 0: Establish connecting to a PKI Provider (CA)
##### Description
This is to establish the Trust relationship between the PKI provider and Cumulocity.
#### Actor
A cumulocity Administrator which has the permissions to the Administration App.
#### Preconditions
The user have an existing account of an PKI Provider (CA) and an existing public certificate of them
#### Triggers
none
#### Basic Flow
The administrator uploads the provided CA to the cumulocity tenant.
#### Alternative Path
The upload of the certificate is not possible of a certificate already exists
#### Amount of involved devices
- [ ] one
- [ ] multiple


## UC 1: Device Registration with existing certificate
#### Description
This is a short Use Case to make sure that we can upload a certificate of a device provided by an external CA.

#### Actor
The cumulocity user which has the permissions to manage devices and access to the device management app

#### Preconditions
One trust relationship to the CA is established

#### Triggers
none

#### Basic Flow
The user uploads the device certificate provided by the CA to the tenant.

#### Alternative Path
none
#### Amount of involved devices
- [ ] ~~one~~
- [ ] ~~multiple~~

## UC 2: Basic authentification replaced by certificates
#### Description

This use case replaces the existing basic authentication method of a device with the authentication method by using a provide device certificates from an external provider.

#### Actor
The cumulocity user which has the permissions to manage devices and access to the device management app.

#### Preconditions
* One trust relationship to the CA is established
* The device is online
* The replacement button is visable and therefore the operation c8y_replaceAuthentication exists in c8y_supportedOperations
* The replacement button is NOT visable if the Agent does not support the replacement process

#### Triggers
The user hits a Button to start the authentication replacement process.

#### Basic Flow
The process starts by sending an operation (c8y_replaceAuthentication)to the device. The device (agent) than generates with ssh keygen a private and apublic key. The key will than be send to the PKI provider. The PKI provider generates a certificate which is send and stored to cumulocity. The microservice create another operation (c8y_sendCertificate) to the device. The device than replaces the Basic Authentication with the certificate.

#### Alternative Path
none
#### Amount of involved devices
- [x] one
- [ ] ~~multiple~~

## UC 3: Certificate Create
#### Description
The use case is to create one or multiple certificates to later on use them for a devices. For devices which are on- and offline.

#### Actor
The cumulocity user which has the permissions to manage devices and access to the device management app.

#### Preconditions
One trust relationship to the CA is established

#### Triggers
none 

#### Basic Flow

The process starts by hitting a button and the amout of certificates are created on the PKI Provider side.


#### Alternative Path
none
#### Amount of involved devices
- [x] one
- [ ] ~~multiple~~

## UC 4: Certificate Renewal
#### Description
This use case is to renew a device certificate automatically and manually.

#### Actor
The cumulocity user which has the permissions to manage devices and access to the device management app.


#### Preconditions

* The device certificate is nearly to be expired but is still valid
* The device certificate is expired
* The device certificate is valid and not close to the eypiry date
* The device needs to be online

#### Triggers

* A button to renew a certificate
* a particular date with a particular time


#### Basic Flow

The system send a request to the PKI provider to renew an existing certificate.  The PKI provider generates a certificate which is send and stored to cumulocity. The microservice create an operation (c8y_sendCertificate) to the device. The device than replaces the old certificate with the new one with the certificate.

#### Alternative Path
none
#### Amount of involved devices
- [x] one
- [x] multiple


## UC 5: Certificate Revokation
#### Description
This use case is to revoke a device certificate automatically or manually.

#### Actor
The cumulocity user which has the permissions to manage devices and access to the device management app.


#### Preconditions
none

#### Triggers

* A button to revoke a certificate

#### Basic Flow

The process revokes a certificate within Cumulocity and within the PKI provider

#### Alternative Path
none
#### Amount of involved devices
- [x] one
- [x] multiple

## UC 6: Certificate Status
#### Description

This use case is to update the status of a device certificate

#### Actor
The cumulocity user which has the permissions to manage devices and access to the device management app.

#### Preconditions
none

#### Triggers

* A button to get the status of a certificate and to update it's details

#### Basic Flow

Ths system request the status of a certificate from the PKI Provider

#### Alternative Path
none
#### Amount of involved devices
- [x] one
- [x] multiple

## UC 7: Certificate Replacement
#### Description
This use case is to replace an existing device certificate with a new one to for example move a device from one cumulocity tenant to another tenant.

#### Actor
The cumulocity user which has the permissions to manage devices and access to the device management app.

#### Preconditions

* the device is already connected with device certificate to the platform
* 

#### Triggers
* A button to replace a device certificate from a tenant
* A button to replace a device certificate from an Enterprise Tenant

#### Basic Flow

Ther system requests a new certificates for a device. A Possible new TenantId is included as well

#### Alternative Path

needs to be defined

#### Amount of involved devices
- [x] one
- [x] multiple

## UC 8: Certificate Reinstate
#### Description

This use case is to reinstate formerly revoked certificates

#### Actor
The cumulocity user which has the permissions to manage devices and access to the device management app.


#### Preconditions

* The device is offline and it's certificate is revoked

#### Triggers

* A button to revoke a device certificate from a tenant

#### Basic Flow

The system sends a reinstate request to the PKI-Provider to reinstate a device certificate 

#### Alternative Path

none
#### Amount of involved devices
- [x] one
- [x] multiple

## UC 9: Certificate Deletion
#### Description

This use case is to delete an existing certificate.

#### Actor

The cumulocity user which has the permissions to manage devices and access to the device management app.

#### Preconditions
none

#### Triggers

* A button to delete a device certificate from a tenant

#### Basic Flow

The user presses a button and have to accept a second time the deletion and then the certificate is deleted.

#### Alternative Path
none
#### Amount of involved devices
- [x] one
- [x] multiple
## UC 10: Certificate Distribution via Gateway
#### Description

This use is to distribute a device certificate to child devices which are attached to a Gateway

#### Actor
The cumulocity user which has the permissions to manage devices and access to the device management app.

#### Preconditions

* The gateway is already with device certificate connected 2 Cumulocity
* The Child devices (agents) can deal with device certificates and can switch between Basic Authentication 2 device certificates
#### Triggers

* The user presses a button to start the process and selects one or multiple child devices.

#### Basic Flow

To be discussed

#### Alternative Path

To be discussed
#### Amount of involved devices
- [x] one
- [x] multiple