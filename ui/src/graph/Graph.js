import React, {useState} from 'react';
import ReactECharts from 'echarts-for-react';
import {InputNumber} from "antd";
import {useSubscription} from "react-stomp-hooks";

const Graph = () => {
  const [yData, setYData] = useState([])
  const [xData, setXData] = useState([])
  useSubscription("/topic/price", (message) => {
    setYData((prev) => prev.concat(message.body).slice(-100));
    setXData((prev) => prev.concat(prev.length > 0 ? prev[prev.length - 1] + 1 : 0).slice(-100));
  });

  const option = {
    xAxis: {
      type: 'category',
      data: xData
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        data: yData,
        type: 'line'
      }
    ]
  }
  return (
    <div>
      <ReactECharts option={option}/>
    </div>
  );
}

export default Graph;
