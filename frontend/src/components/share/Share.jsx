import "./share.css"
import {PermMedia, PostAdd} from "@mui/icons-material"

export default function Share() {
  return (
    <div className="share">
        <div className="shareWrapper">
            <div className="shareTop">
                <img className="shareProfileImg" src="" alt=""/>
                <input placeholder="Objavi..." className="shareInput" />
            </div>
            <hr className="shareHr"/>
            <div className="shareBottom">
                <div className="shareOptions">
                    <div className="shareOption">
                        <PermMedia className="shareIcon"/>
                        <span className="shareOptionText">Media</span>
                    </div>
                </div>
                <button className="shareButton"><PostAdd/>Postavi</button>
            </div>
        </div>
    </div>
  )
}
