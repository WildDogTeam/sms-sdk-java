# wilddog-sms

Wilddog SMS SDK for Java

## Getting started

### 初始化

```xml
<dependency>
    <groupId>com.wilddog.sms</groupId>
    <artifactId>sms-client</artifactId>
    <version>1.0.1</version>
</dependency>
```

```java
SmsConfig smsConfig = new SmsConfig(<app_id>, <secret>);
SmsClinet clinet = new SmsClient(smsConfig);
```

### 发送验证码短信

```java
SmsResponse smsResponse = client.sendCode(String mobile, String templateId, ArrayList<String> params)
其中
smsResponse.isSuccess()获取本次请求是否成功
smsResponse.getRrid()获取本次发送的批次id
smsResponse.getWilddogError()获取发送失败信息
```

### 校验验证码

```java
SmsResponse smsResponse = client.checkCode(String mobile, String code)
其中
smsResponse.isSuccess()获取本次请求是否成功
smsResponse.getWilddogError()获取发送失败信息
```


### 发送通知短信

```java
SmsResponse smsResponse = client.sendNotify(List<String> mobiles, String templateId, ArrayList<String> params)
其中
smsResponse.isSuccess()获取本次请求是否成功
smsResponse.getRrid()获取本次发送的批次id
smsResponse.getWilddogError()获取发送失败信息
```

### 查询发送状态

```java
StatusQueryResponse statusQueryResponse = client.queryStatus(string rrid)
statusQueryResponse.isSuccess()获取本次请求是否成功
statusQueryResponse.getStatus()获取本次请求的状态,成功获取短信状态该值为'success'
statusQueryResponse.getData()获取本次请求短信状态列表 返回的是一个List<Map>,
其中key列表包括

	status 代表短信的发送状态 为int类型
	mobile 代表短信的手机号
	deliveryStatus 代表短信的网关状态 成功状态为DELIVRD
	rrid 代表本次的批次号
	receiveTime 代表短信状态恢复时间

statusQueryResponse.getWilddogError()获取查询失败信息
```

### 查询账户余额


```java
BalanceResponse balanceResponse = client.queryBalance();
其中
balanceResponse.isSuccess() 获取本次请求是否成功
balanceResponse.getStatus() 获取本次请求的状态,成功获取账户余额值为’success‘
balanceResponse.getBalance() 获取账户的余额 单位是厘
balanceResponse.getVoucherBalance() 获取账户的代金券 单位是厘
balanceResponse.getWilddogError() 获取账户余额失败的失败信息
```