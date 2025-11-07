# 数据看板API接口文档

## 概述
数据看板模块提供了健身房运营数据的统计和分析接口，支持按时间范围查询各种业务指标。

## 接口列表

### 1. 获取KPI指标数据
**接口地址：** `GET /system/dashboard/kpis`

**请求参数：**
- `startDate` (String): 开始日期，格式：yyyy-MM-dd
- `endDate` (String): 结束日期，格式：yyyy-MM-dd

**响应示例：**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "totalMembers": 1256,
    "memberGrowth": 12.5,
    "totalCheckIns": 3420,
    "checkInGrowth": 15.2,
    "activityRate": 68.5,
    "activityGrowth": 5.1
  }
}
```

### 2. 获取会员增长趋势数据
**接口地址：** `GET /system/dashboard/member-growth`

**请求参数：**
- `startDate` (String): 开始日期
- `endDate` (String): 结束日期

**响应示例：**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": [
    {
      "date": "1月",
      "newMembers": 45,
      "totalMembers": 1200
    },
    {
      "date": "2月",
      "newMembers": 52,
      "totalMembers": 1252
    }
  ]
}
```

### 3. 获取课程预约Top5数据
**接口地址：** `GET /system/dashboard/class-booking-top5`

**请求参数：**
- `startDate` (String): 开始日期
- `endDate` (String): 结束日期

**响应示例：**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": [
    {
      "name": "瑜伽课程",
      "bookings": 456
    },
    {
      "name": "动感单车",
      "bookings": 378
    }
  ]
}
```

### 4. 获取训练高峰期数据
**接口地址：** `GET /system/dashboard/peak-hours`

**请求参数：**
- `startDate` (String): 开始日期
- `endDate` (String): 结束日期

**响应示例：**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "weekday": [
      {
        "time": "06:00",
        "activeUsers": 25
      }
    ],
    "weekend": [
      {
        "time": "08:00",
        "activeUsers": 15
      }
    ],
    "morning": [
      {
        "time": "06:00",
        "activeUsers": 25
      }
    ],
    "evening": [
      {
        "time": "17:00",
        "activeUsers": 55
      }
    ]
  }
}
```

### 5. 获取会员详细数据
**接口地址：** `GET /system/dashboard/member-details`

**请求参数：**
- `startDate` (String): 开始日期
- `endDate` (String): 结束日期

**响应示例：**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": [
    {
      "type": "年度会员",
      "count": 456,
      "percentage": 36.3,
      "avgRevenue": 2400
    }
  ]
}
```

### 6. 获取课程详细数据
**接口地址：** `GET /system/dashboard/class-details`

**请求参数：**
- `startDate` (String): 开始日期
- `endDate` (String): 结束日期

**响应示例：**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": [
    {
      "name": "瑜伽课程",
      "bookings": 456,
      "attendanceRate": 92.0,
      "satisfaction": 95.0
    }
  ]
}
```

### 7. 刷新数据看板缓存
**接口地址：** `POST /system/dashboard/refresh-cache`

**请求参数：** 无

**响应示例：**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": "缓存刷新成功"
}
```

## 使用说明

1. **时间范围参数**：所有接口都支持通过 `startDate` 和 `endDate` 参数指定查询的时间范围
2. **缓存机制**：数据查询结果会被缓存，提高响应速度
3. **错误处理**：所有接口都有统一的错误处理机制
4. **数据格式**：所有数值类型的数据都保留适当的小数位数

## 前端集成示例

```javascript
// 获取KPI数据
async function getKpiData(startDate, endDate) {
  const response = await fetch(`/system/dashboard/kpis?startDate=${startDate}&endDate=${endDate}`);
  return await response.json();
}

// 获取会员增长数据
async function getMemberGrowthData(startDate, endDate) {
  const response = await fetch(`/system/dashboard/member-growth?startDate=${startDate}&endDate=${endDate}`);
  return await response.json();
}

// 获取课程预约Top5数据
async function getClassBookingTop5(startDate, endDate) {
  const response = await fetch(`/system/dashboard/class-booking-top5?startDate=${startDate}&endDate=${endDate}`);
  return await response.json();
}
```
