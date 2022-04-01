import React from 'react'
import "antd/dist/antd.css";
import {Content} from "antd/lib/layout/layout";
import { useNavigate } from "react-router-dom";
import {Button, Col, Form, Input, Row, Select} from "antd";
import TitleHeader from "../../Components/TitleHeader";
import {CustomLayout} from "../../Components/CustomLayout";
import axios from "axios";
import {decode as base64_decode, encode as base64_encode} from 'base-64';

const { Option } = Select;


const Login = () => {

    const navigate = useNavigate();

    const scrollHeight = Math.max(
        document.body.scrollHeight,
        document.documentElement.scrollHeight,
        document.body.offsetHeight,
        document.documentElement.offsetHeight,
        document.body.clientHeight,
        document.documentElement.clientHeight
    );

    const onFinish = (values) => {
        const { network, descriptor } = values

        console.log(network);
        console.log(descriptor);

        let encodedDescriptor = base64_encode(descriptor);

        const data = {
            "network": network,
            "descriptor": encodedDescriptor
        }

        axios.put('http://localhost:8080/wallet', data, {withCredentials: true})
            .then(response => {
                console.log("Success redirecting the user to API Catalog page.")
                navigate('/api1');
            }) //print wallet request
    };

    return (<CustomLayout>
        <Content
            style={{
                padding: "50px 50px",
                marginTop: "30px",
                height: `${scrollHeight}px`,
            }}
        >
            <Row justify="center">
                <Col xs={24}>
                    <TitleHeader
                        title="Welcome Back! Please Log In"
                        size={2}
                    />
                </Col>
            </Row>
            <Row justify="center" style={{marginTop: "30px"}}>
                <Col xs={24} sm={18} md={12} lg={10} xl={5}>
                    <Form
                        name="normal_login"
                        className="login-form"
                        initialValues={{remember: true}}
                        onFinish={onFinish}
                    >
                        <Form.Item
                            name="network"
                            rules={[
                                {
                                    required: true,
                                    message: "Please select Network!",
                                },
                            ]}
                        >
                            <Select style={{width: 120}}>
                                <Option value="testnet">testnet</Option>
                                <Option value="bitcoin">bitcoin</Option>
                                <Option value="regtest">regtest</Option>
                            </Select>
                        </Form.Item>
                        <Form.Item
                            name="descriptor"
                            rules={[
                                {
                                    required: true,
                                    message: "Please input your Descriptor!",
                                },
                            ]}
                        >
                            <Input
                                placeholder="Descriptor"
                                style={{
                                    width: "40%",
                                    marginBottom: "10px",
                                }}
                            />
                        </Form.Item>
                        <Form.Item>
                            <Button
                                type="primary"
                                htmlType="submit"
                                style={{
                                    width: "20%",
                                    marginBottom: "10px",
                                }}
                            >
                                Log in
                            </Button>
                        </Form.Item>
                    </Form>
                </Col>
            </Row>
        </Content>
        </CustomLayout>);
};

export default Login;