import "./topbar.css"
import {Home, Work, Search, SavedSearch, Person, Chat, Notifications} from "@mui/icons-material"

export default function Topbar() {
  return (
    <div className="topbarContainer">
        <div className="topbarLeft">
          <span className="logo">Profesionalac</span>
        </div>
        <div className="topbarCenter">
          <div className="topbarLinks">
            <div className="topbarIconItem">
              <Home/>
            </div>
            <div className="topbarIconItem">
              <Work/>
            </div>
            <div className="topbarIconItem">
              <Chat/>
            </div>
            <div className="topbarIconItem">
              <SavedSearch/>
            </div>
          </div>
        </div>
        <div className="topbarRight">
        <div className="searchbar">
            <Search className="searchIcon"/>
            <input placeholder="Pretraga" className="searchInput" />
          </div>
          <div className="topbarIcons">
            <div className="topbarIconItem">
              <Person/>
              <span className="topbarIconBadge">1</span>
            </div>
            <div className="topbarIconItem">
              <Notifications/>
              <span className="topbarIconBadge">1</span>
            </div>
          </div>
          <img src="" alt="profile" className="topbarImg" />
        </div>
    </div>
  )
}
