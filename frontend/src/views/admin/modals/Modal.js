import React, { useEffect } from "react";
import ReactDOM from "react-dom";
import { CSSTransition } from "react-transition-group";
import "./modal.css";
import Button from "@material-ui/core/Button";

const Modal = props => {
    const closeOnEscapeKeyDown = e => {
        if ((e.charCode || e.keyCode) === 27) {
            props.onClose();
        }
    };

    useEffect(() => {
        document.body.addEventListener("keydown", closeOnEscapeKeyDown);
        return function cleanup() {
            document.body.removeEventListener("keydown", closeOnEscapeKeyDown);
        };
    }, []);

    return ReactDOM.createPortal(
        <CSSTransition
            in={props.show}
            unmountOnExit
            timeout={{ enter: 0, exit: 300 }}
        >
            <div className="modal" onClick={props.onClose}>
                <div className="modal-content" onClick={e => e.stopPropagation()}>
                    <div className="modal-header">
                        <h2 className="modal-title">{props.title}</h2>
                    </div>
                    <div className="modal-body">{props.children}</div>
                    <div className="modal-footer">
                        <Button onClick={props.onClose}
                                className="button"
                                variant="contained"
                                color="primary"
                                size="medium"
                                style={{textAlign:'center'}}
                                >
                            Done
                        </Button>
                    </div>
                </div>
            </div>
        </CSSTransition>,
        document.getElementById("root")
    );
};

export default Modal;