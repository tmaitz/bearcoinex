import React, {useEffect, useState} from 'react';
import ReactECharts from 'echarts-for-react';
import useWebSocket, {ReadyState} from 'react-use-websocket'
import {InputNumber} from "antd";

const Graph = () => {
  const [yData, setYData] = useState([])
  const [xData, setXData] = useState([])
  const { sendMessage, lastMessage, readyState } = useWebSocket("ws://localhost:8080/ws-chat");

  useEffect(() => {
    if (lastMessage !== null) {
      setYData((prev) => prev.concat(lastMessage.data).slice(-100));
      setXData((prev) => prev.concat(prev.length > 0 ? prev[prev.length - 1] + 1 : 0).slice(-100));
    }
  }, [lastMessage]);
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
      <InputNumber min={1} max={30} defaultValue={1} onChange={v => sendMessage(v)} />
      <ReactECharts option={option}/>
    </div>
  );
}

export default Graph;
