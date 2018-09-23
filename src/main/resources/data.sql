-- Load Initial Data

INSERT INTO ADDRESS (ADDRESS_ID, ADDRESS_LINE1, ADDRESS_LINE2, CITY, STATE, COUNTRY, POSTAL_CODE)
VALUES (1,'Merchant Street','192/11','Mumbai', 'Maharashtra', 'India','400123');

INSERT INTO CUSTOMER (CUSTOMER_ID, FIRST_NAME,LAST_NAME, PRIMARY_EMAIL, PRIMARY_PHONE_NUMBER, PRIMARY_ADDRESS_ADDRESS_ID)
VALUES (1,'Cardinal','Tom B. Erichsen','skagen21@gmail.com', '1234567890', 1);

INSERT INTO ACCOUNT (ACCOUNT_ID, ACCOUNT_TYPE ,BALANCE, CUSTOMER_ID)
VALUES (1,'PRIMARY','10000', 1);