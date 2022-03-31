import { Fragment } from "react";
import { Divider, Typography } from "antd";

const { Title } = Typography;

const TitleHeader = (props) => {
    const size = props.size ?? 3;
    return (
        <Fragment>
            <Title level={size}>{props.title}</Title>
            <Divider
                style={{
                    borderTop: "1px solid gray",
                }}
            />
        </Fragment>
    );
};

export default TitleHeader;
