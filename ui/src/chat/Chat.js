import React, {useEffect, useState} from 'react';
import useWebSocket, {ReadyState} from 'react-use-websocket'
import {Button, Divider, Input, List, Typography} from "antd";

const Chat = () => {
  const [message, setMessage] = useState([])
  const [messages, setMessages] = useState([])
  const {sendMessage, lastMessage, readyState} = useWebSocket("ws://localhost:8080/ws-chat");

  useEffect(() => {
    if (lastMessage !== null) {
      setMessages((prev) => prev.concat(JSON.parse(lastMessage.data)).slice(-100));
    }
  }, [lastMessage]);
  return (
    <div>
      <Typography.Title>
        Страница МедведьМонеты
      </Typography.Title>
      <div>
        <Input style={{width: 500}} placeholder={"Напишите что-нибудь тут..."} onChange={v => setMessage(v.target.value)}/>
        <Button type="primary" onClick={event => sendMessage(message)}>Отправить</Button>
      </div>
      <Divider />
      <List
        loading={readyState !== ReadyState.OPEN}
        itemLayout="horizontal"
        dataSource={messages}
        renderItem={(item) => (
            <List.Item>
              <List.Item.Meta
                title={<Typography.Text>{item.author}</Typography.Text>}
                description={<Typography.Text>{item.text}</Typography.Text>}
              />
            </List.Item>
        )}
      />
    </div>
  );
}

export default Chat;
