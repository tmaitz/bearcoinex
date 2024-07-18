import React, {useEffect, useState} from 'react';
import {useStompClient, useSubscription} from 'react-stomp-hooks';
import {Button, Divider, Input, List, Typography} from "antd";

const Chat = () => {
  const [message, setMessage] = useState([])
  const [messages, setMessages] = useState([])

  useSubscription("/topic/chat", (message) => {
    setMessages((prev) => prev.concat(JSON.parse(message.body)).slice(-100));
  });

  const stompClient = useStompClient();

  return (
    <div>
      <Typography.Title>
        Страница МедведьМонеты
      </Typography.Title>
      <div>
        <Input style={{width: 500}} placeholder={"Напишите что-нибудь тут..."} onChange={v => setMessage(v.target.value)}/>
        <Button type="primary" onClick={event => {
          if (stompClient) {
            //Send Message
            stompClient.publish({
              destination: "/app/message",
              body: JSON.stringify({
                author: "UI",
                text: message
              }),
            });
          } else {
            //Handle error
          }
        }}>Отправить</Button>
      </div>
      <Divider />
      <List
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
