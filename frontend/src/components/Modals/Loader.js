import React from "react";
import ReactDOM from "react-dom";
import { CSSTransition } from "react-transition-group";
import "../../assets/css/modal.css";
import {PuffLoader} from "react-spinners";
import {css} from "@emotion/react";


const override = css`
  display: block;
  margin: 0 auto;
  border-color: red;
`;


const Loader = props => {

    return ReactDOM.createPortal(
        <CSSTransition
            in={props.show}
            timeout={{ enter: 0, exit: 300 }}
        >
            <div className="modal">
                <div className="modal-content" onClick={e => e.stopPropagation()}>
                    <div className="modal-body">
                        <PuffLoader css={override} size={150} color={"#123abc"} loading={true} speedMultiplier={1.5} />
                    </div>
                </div>
            </div>
        </CSSTransition>,
        document.getElementById("root")
    );
};

export default Loader;
