import { StompSessionProvider } from 'react-stomp-hooks';
import Chat from "./chat/Chat";
import Graph from "./graph/Graph";
import 'antd/lib/style/reset.css';

function App() {
  return (
    <StompSessionProvider url={"ws://localhost:8080/ws-data"}>
      <div className="App">
        <Graph/>
        <Chat/>
      </div>
    </StompSessionProvider>
  );
}

export default App;
