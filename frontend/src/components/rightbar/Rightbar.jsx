import "./rightbar.css"
import { WorkHistory } from "@mui/icons-material"

export default function Rightbar() {
  return (
    <div className="rightbar">
      <div className="rightbarWrapper">
        <ul className="rightbarList">
          <li className="rightbarListItem">
            <WorkHistory className="rightbarIcon"/>
            Aplikacija 1
          </li>
          <li className="rightbarListItem">
            <WorkHistory className="rightbarIcon"/>
            Aplikacija 2
          </li>
        </ul>
      </div>
    </div>
  )
}
